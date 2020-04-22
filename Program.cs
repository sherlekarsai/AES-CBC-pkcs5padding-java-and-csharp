using System;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
/// <summary>
/// aes/cbc/pkcs5padding c#
/// </summary>
namespace RefreshFileEncryption
{
    class Program
    {
        private static String key = "mynameisnotasAES";//Keep 16 character length 
        private static String initVector = "RandomDtaaValeue";//Keep 16 character length 
        private static AesManaged CreateAes()
        {
            var aes = new AesManaged();
            aes.Key = System.Text.Encoding.UTF8.GetBytes(key); 
            aes.IV = System.Text.Encoding.UTF8.GetBytes(initVector);
            return aes;
        }
        public static string TextToHex(byte[] text)
        {
            StringBuilder hex = new StringBuilder(text.Length * 2);
            try
            {
                foreach (byte b in text)
                {
                    hex.AppendFormat("{0:x2}", b);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(DateTime.Now + ">> Error: Byte array to hex convert error." + e.ToString());
            }
            return hex.ToString().ToUpper();
        }
        public static byte[] HexToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length)
                             .Where(x => x % 2 == 0)
                             .Select(x => Convert.ToByte(hex.Substring(x, 2), 16))
                             .ToArray();
        }
        public static string encrypt(string text)
        {
            using (AesManaged aes = CreateAes())
            {
                aes.Mode = CipherMode.CBC;
                aes.Padding = PaddingMode.PKCS7;
                ICryptoTransform encryptor = aes.CreateEncryptor();
                using (MemoryStream ms = new MemoryStream())
                {
                    using (CryptoStream cs = new CryptoStream(ms, encryptor, CryptoStreamMode.Write))
                    {
                        using (StreamWriter sw = new StreamWriter(cs))
                            sw.Write(text);
                        return TextToHex(ms.ToArray());
                    }
                }
            }
        }
        public static string decrypt(string text)
        {
            using (var aes = CreateAes())
            {
                aes.Mode = CipherMode.CBC;
                aes.Padding = PaddingMode.PKCS7;
                ICryptoTransform decryptor = aes.CreateDecryptor();
                using (MemoryStream ms = new MemoryStream(HexToByteArray(text)))
                {
                    using (CryptoStream cs = new CryptoStream(ms, decryptor, CryptoStreamMode.Read))
                    {
                        using (StreamReader reader = new StreamReader(cs))
                        {
                            return reader.ReadToEnd();
                        }
                    }
                }

            }
        }
        static void Main(string[] args)
        {
            String originalString = "00000003FT";
            Console.WriteLine(originalString);
            String encryptedString = encrypt(originalString);
            Console.WriteLine(encryptedString);
            Console.WriteLine(encryptedString);
            String decryptedString = decrypt(encryptedString);
            Console.WriteLine(decryptedString);


        }
    }
}
