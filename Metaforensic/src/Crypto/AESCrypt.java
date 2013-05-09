package Crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Enumeration;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 * The Class AESCrypt.
 *
 * @author Vócali Sistemas Inteligentes
 */
public final class AESCrypt {

    /**
     * The Constant JCE_EXCEPTION_MESSAGE.
     */
    private static final String JCE_EXCEPTION_MESSAGE = "Por favor asegurate de tener instalado el "
            + "\"Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files\" "
            + "(http://java.sun.com/javase/downloads/index.jsp) de aqui puedes descargarlo.";
    /**
     * The Constant RANDOM_ALG.
     */
    private static final String RANDOM_ALG = "SHA1PRNG";
    /**
     * The Constant DIGEST_ALG.
     */
    private static final String DIGEST_ALG = "SHA-256";
    /**
     * The Constant HMAC_ALG.
     */
    private static final String HMAC_ALG = "HmacSHA256";
    /**
     * The Constant CRYPT_ALG.
     */
    private static final String CRYPT_ALG = "AES";
    /**
     * The Constant CRYPT_TRANS.
     */
    private static final String CRYPT_TRANS = "AES/CBC/NoPadding";
    /**
     * The Constant DEFAULT_MAC.
     */
    private static final byte[] DEFAULT_MAC = {0x01, 0x23, 0x45, 0x67,
        (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef};
    /**
     * The Constant KEY_SIZE.
     */
    private static final int KEY_SIZE = 32;
    /**
     * The Constant BLOCK_SIZE.
     */
    private static final int BLOCK_SIZE = 16;
    /**
     * The Constant SHA_SIZE.
     */
    private static final int SHA_SIZE = 32;
    /**
     * The param.
     */
    private SecurityFile param = SecurityFile.getInstance();
    /**
     * The password.
     */
    private byte[] password;
    /**
     * The cipher.
     */
    private Cipher cipher;
    /**
     * The hmac.
     */
    private Mac hmac;
    /**
     * The random.
     */
    private SecureRandom random;
    /**
     * The digest.
     */
    private MessageDigest digest;
    /**
     * The iv spec1.
     */
    private IvParameterSpec ivSpec1;
    /**
     * The aes key1.
     */
    private SecretKeySpec aesKey1;
    /**
     * The iv spec2.
     */
    private IvParameterSpec ivSpec2;
    /**
     * The aes key2.
     */
    private SecretKeySpec aesKey2;

    /**
     * Generates a pseudo-random byte array.
     *
     * @param len the len
     * @return pseudo-random byte array of <tt>len</tt> bytes.
     */
    protected byte[] generateRandomBytes(int len) {
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * SHA256 digest over given byte array and random bytes.
     *
     * <tt>bytes.length</tt> * <tt>num</tt> random bytes are added to the
     * digest.
     *
     *
     * The generated hash is saved back to the original byte array.
     *
     * Maximum array size is {@link #SHA_SIZE} bytes.
     *
     * @param bytes the bytes
     * @param num the num
     */
    protected void digestRandomBytes(byte[] bytes, int num) {
        assert bytes.length <= SHA_SIZE;

        digest.reset();
        digest.update(bytes);
        for (int i = 0; i < num; i++) {
            random.nextBytes(bytes);
            digest.update(bytes);
        }
        System.arraycopy(digest.digest(), 0, bytes, 0, bytes.length);
    }

    /**
     * Generates a pseudo-random IV based on time and this computer's MAC.
     *
     *
     * This IV is used to crypt IV 2 and AES key 2 in the file.
     *
     * @return IV.
     */
    protected byte[] generateIv1() {
        byte[] iv = new byte[BLOCK_SIZE];
        long time = System.currentTimeMillis();
        byte[] mac = null;
        try {
            @SuppressWarnings("rawtypes")
            Enumeration ifaces = NetworkInterface.getNetworkInterfaces();
            while (mac == null && ifaces.hasMoreElements()) {
                mac = ((NetworkInterface) ifaces.nextElement())
                        .getHardwareAddress();
            }
        } catch (Exception e) {
            // Ignore.
        }
        if (mac == null) {
            mac = DEFAULT_MAC;
        }

        for (int i = 0; i < 8; i++) {
            iv[i] = (byte) (time >> (i * 8));
        }
        System.arraycopy(mac, 0, iv, 8, mac.length);
        digestRandomBytes(iv, 256);
        return iv;
    }

    /**
     * Generates an AES key starting with an IV and applying the supplied user
     * password.
     *
     *
     * This AES key is used to crypt IV 2 and AES key 2.
     *
     * @param iv the iv
     * @param password the password
     * @return AES key of {@link #KEY_SIZE} bytes.
     */
    protected byte[] generateAESKey1(byte[] iv, byte[] password) {
        byte[] aesKey = new byte[KEY_SIZE];
        System.arraycopy(iv, 0, aesKey, 0, iv.length);
        for (int i = 0; i < 8192; i++) {
            digest.reset();
            digest.update(aesKey);
            digest.update(password);
            aesKey = digest.digest();
        }
        return aesKey;
    }

    /**
     * Generates the random IV used to crypt file contents.
     *
     * @return IV 2.
     */
    protected byte[] generateIV2() {
        byte[] iv = generateRandomBytes(BLOCK_SIZE);
        digestRandomBytes(iv, 256);
        return iv;
    }

    /**
     * Generates the random AES key used to crypt file contents.
     *
     * @return AES key of {@link #KEY_SIZE} bytes.
     */
    protected byte[] generateAESKey2() {
        byte[] aesKey = generateRandomBytes(KEY_SIZE);
        digestRandomBytes(aesKey, 32);
        return aesKey;
    }

    /**
     * Utility method to read bytes from a stream until the given array is fully
     * filled.
     *
     * @param in the in
     * @param bytes the bytes
     * @throws IOException if the array can't be filled.
     */
    protected void readBytes(InputStream in, byte[] bytes, String toPath, OutputStream out) throws IOException {
        if (in.read(bytes) != bytes.length) {
            out.close();
            File tmp = new File(toPath);
            tmp.delete();
            JOptionPane.showMessageDialog(null, "Fin de archivo inesperado",
                    "Error de archivo", JOptionPane.ERROR_MESSAGE);
            throw new IOException("Fin de archivo inesperado");
        }
    }

    /**
     * ************ PUBLIC API * ************.
     *
     * @param password the password
     * @throws GeneralSecurityException the general security exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    /**
     * Builds an object to encrypt or decrypt files with the given password.
     *
     * @throws GeneralSecurityException if the platform does not support the
     * required cryptographic methods.
     * @throws UnsupportedEncodingException if UTF-16 encoding is not supported.
     */
    public AESCrypt(String password) throws GeneralSecurityException,
            UnsupportedEncodingException {
        this(false, password);
    }

    /**
     * Builds an object to encrypt or decrypt files with the given password.
     *
     * @param debug the debug
     * @param password the password
     * @throws GeneralSecurityException if the platform does not support the
     * required cryptographic methods.
     * @throws UnsupportedEncodingException if UTF-16 encoding is not supported.
     */
    public AESCrypt(boolean debug, String password)
            throws GeneralSecurityException, UnsupportedEncodingException {
        try {
            setPassword(password);
            random = SecureRandom.getInstance(RANDOM_ALG);
            digest = MessageDigest.getInstance(DIGEST_ALG);
            cipher = Cipher.getInstance(CRYPT_TRANS);
            hmac = Mac.getInstance(HMAC_ALG);
        } catch (NoClassDefFoundError | ExceptionInInitializerError | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, JCE_EXCEPTION_MESSAGE,
                    "Error de Java ", JOptionPane.ERROR_MESSAGE);
            throw new GeneralSecurityException(JCE_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Changes the password this object uses to encrypt and decrypt.
     *
     * @param password the new password
     * @throws UnsupportedEncodingException if UTF-16 encoding is not supported.
     */
    public void setPassword(String password)
            throws UnsupportedEncodingException {
        this.password = password.getBytes("UTF-16LE");
    }

    /**
     * The file at <tt>fromPath</tt> is decrypted and saved at <tt>toPath</tt>
     * location.
     *
     *
     * Source file can be encrypted using version 1 or 2 of aescrypt.
     *
     * @param fromPath the from path
     * @param toPath the to path
     * @throws IOException when there are I/O errors.
     * @throws GeneralSecurityException if the platform does not support the
     * required cryptographic methods.
     */
    public void decrypt(String fromPath, String toPath) throws IOException,
            GeneralSecurityException {
        InputStream in = null;
        OutputStream out = null;
        @SuppressWarnings("UnusedAssignment")
        byte[] text = null, backup = null;
        long total = 3 + 1 + 1 + BLOCK_SIZE + BLOCK_SIZE + KEY_SIZE + SHA_SIZE
                + 1 + SHA_SIZE;
        int version;
        try {
            in = new FileInputStream(fromPath);
            out = new FileOutputStream(toPath);
            text = new byte[3];
            readBytes(in, text, toPath, out); // Heading.
            if (!new String(text, "UTF-8").equals("AES")) {
                out.close();
                File tmp = new File(toPath);
                tmp.delete();
                JOptionPane.showMessageDialog(null,
                        "Encabezado de archivo no válido",
                        "Error de codificación",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException("Encabezado de archivo no válido");
            }

            version = in.read(); // Version.
            if (version < 1 || version > 2) {
                out.close();
                File tmp = new File(toPath);
                tmp.delete();
                JOptionPane.showMessageDialog(null,
                        "El número de versión no compatible: " + version,
                        "Error de configuración",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException("El número de versión no compatible: "
                        + version);
            }
            in.read(); // Reserved.

            if (version == 2) { // Extensions.
                text = new byte[2];
                int len;
                do {
                    readBytes(in, text, toPath, out);
                    len = ((0xff & (int) text[0]) << 8)
                            | (0xff & (int) text[1]);
                    if (in.skip(len) != len) {
                        out.close();
                        File tmp = new File(toPath);
                        tmp.delete();
                        JOptionPane.showMessageDialog(null,
                                "Fin inesperado de la extensión",
                                "Error de archivo ",
                                JOptionPane.ERROR_MESSAGE);
                        throw new IOException("Fin inesperado de la extensión");
                    }
                    total += 2 + len;
                } while (len != 0);
            }
            text = new byte[BLOCK_SIZE];
            readBytes(in, text, toPath, out);
            // Initialization Vector.
            ivSpec1 = new IvParameterSpec(text);
            aesKey1 = new SecretKeySpec(generateAESKey1(ivSpec1.getIV(),
                    password), CRYPT_ALG);
            cipher.init(Cipher.DECRYPT_MODE, aesKey1, ivSpec1);
            backup = new byte[BLOCK_SIZE + KEY_SIZE];
            readBytes(in, backup, toPath, out);
            // IV and key to decrypt file contents.
            text = cipher.doFinal(backup);
            ivSpec2 = new IvParameterSpec(text, 0, BLOCK_SIZE);
            aesKey2 = new SecretKeySpec(text, BLOCK_SIZE, KEY_SIZE, CRYPT_ALG);
            hmac.init(new SecretKeySpec(aesKey1.getEncoded(), HMAC_ALG));
            backup = hmac.doFinal(backup);
            text = new byte[SHA_SIZE];
            readBytes(in, text, toPath, out);
            // HMAC and authenticity test.
            if (!Arrays.equals(backup, text)) {
                out.close();
                File tmp = new File(toPath);
                tmp.delete();
                JOptionPane.showMessageDialog(null,
                        "El archivo ha sido alterado o contraseña incorrecta",
                        "Error de autenticación ",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException(
                        "El archivo ha sido alterado o contraseña incorrecta");
            }
            total = new File(fromPath).length() - total;
            // Payload size.
            if (total % BLOCK_SIZE != 0) {
                out.close();
                File tmp = new File(toPath);
                tmp.delete();
                JOptionPane.showMessageDialog(null,
                        "Archivo de entrada está dañado", "Error de archivo",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException("Archivo de entrada está dañado");
            }
            if (total == 0) {
                // Hack: empty files won't enter block-processing for-loop
                // below.
                in.read();
                // Skip last block size mod 16.
            }
            cipher.init(Cipher.DECRYPT_MODE, aesKey2, ivSpec2);
            hmac.init(new SecretKeySpec(aesKey2.getEncoded(), HMAC_ALG));
            backup = new byte[BLOCK_SIZE];
            text = new byte[BLOCK_SIZE];
            for (int block = (int) (total / BLOCK_SIZE); block > 0; block--) {

                int len = BLOCK_SIZE;
                if (in.read(backup, 0, len) != len) {
                    out.close();
                    File tmp = new File(toPath);
                    tmp.delete(); // Cyphertext block.
                    JOptionPane.showMessageDialog(null,
                            "Fin inesperado de contenido del archivo",
                            "Error de archivo",
                            JOptionPane.ERROR_MESSAGE);
                    throw new IOException(
                            "Fin inesperado de contenido del archivo");
                }
                cipher.update(backup, 0, len, text);
                hmac.update(backup, 0, len);
                if (block == 1) {
                    int last = in.read(); // Last block size mod 16.
                    len = (last > 0 ? last : BLOCK_SIZE);
                }
                out.write(text, 0, len);
            }
            out.write(cipher.doFinal());

            backup = hmac.doFinal();
            text = new byte[SHA_SIZE];
            readBytes(in, text, toPath, out); // HMAC and authenticity test.
            if (!Arrays.equals(backup, text)) {
                out.close();
                File tmp = new File(toPath);
                tmp.delete();
                JOptionPane.showMessageDialog(null,
                        "El archivo ha sido alterado o contraseña incorrecta",
                        "Error de autenticación ",
                        JOptionPane.ERROR_MESSAGE);
                throw new IOException(
                        "El archivo ha sido alterado o contraseña incorrecta");
            }
        } catch (InvalidKeyException e) {
            out.close();
            File tmp = new File(toPath);
            tmp.delete();
            JOptionPane.showMessageDialog(null, JCE_EXCEPTION_MESSAGE,
                    "Error de Java ", JOptionPane.ERROR_MESSAGE);
            throw new GeneralSecurityException(JCE_EXCEPTION_MESSAGE, e);
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Process desencriptado
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public void ProcessDe() throws IOException, GeneralSecurityException {
        decrypt(param.getIn(), param.getOut());
    }
}
