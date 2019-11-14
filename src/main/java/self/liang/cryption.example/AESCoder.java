package self.liang.cryption.example;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class AESCoder {
    private final String KEY_ALGORITHM = "AES";
    private final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法

    private final byte[] default_iv = new byte[]{(byte) 0xe0, 0x4f, (byte) 0xd0, 0x20, (byte) 0xea, 0x3a, 0x69, 0x10, (byte) 0xa2, (byte) 0xd8, 0x08, 0x00, 0x2b, 0x30, 0x30, (byte) 0x9d};
    private byte[] default_mkey;
    private Base64 coder = new Base64();

    public AESCoder() {
        try {
            default_mkey = "1234567890ABCDEF".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @param iv       使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 加密数据
     */
    public  byte[] encrypt(byte[] content, byte[] password, byte[] iv) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //密码key(超过16字节即128bit的key，需要替换jre中的local_policy.jar和US_export_policy.jar，否则报错：Illegal key size)
            SecretKeySpec keySpec = new SecretKeySpec(password, KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            //初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            //加密
            byte[] result = cipher.doFinal(content);

            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public  String defaultEncrypt(String str) throws UnsupportedEncodingException {
        byte[] encodedata = encrypt(str.getBytes("utf-8"), default_mkey, default_iv);
        encodedata = coder.encode(encodedata);
        String result = new String(encodedata, "utf-8");
        return result;
    }

    public  String defaultDecrypt(String str) {
        byte[] datas = coder.decode(str);
        String result = decrypt(datas, default_mkey, default_iv);
        return result;
    }


    /**
     * AES 解密操作
     *
     * @param content  密文
     * @param password 密码
     * @param iv       使用CBC模式，需要一个向量iv，可增加加密算法的强度
     * @return 明文
     */
    public  String decrypt(byte[] content, byte[] password, byte[] iv) {

        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //密码key
            SecretKeySpec keySpec = new SecretKeySpec(password, KEY_ALGORITHM);

            //向量iv
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            //初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            //执行操作
            byte[] result = cipher.doFinal(content);

            return new String(result, "utf-8");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public  byte[] getDefault_mkey() {
        return default_mkey;
    }

    public  void setDefault_mkey(byte[] default_mkey) {
        this.default_mkey = default_mkey;
    }

}
