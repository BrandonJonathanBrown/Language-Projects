import pyaes, pbkdf2, binascii, os, secrets

#Author Brandon Jonathan Brown

class FileEncryption:

    def __init__(self):
        print("[*] Starting Application!")
        self.filename = input("Please Enter A Name For The File: ")
        self.text = input("Please Enter Text To Write: ")
        self.password = "34*7^%##@!"  # Consider using a more secure approach
        self.file_text()
        self.key_salt_init()
        self.encrypt()
        self.decrypt()

    def file_text(self):
        """Writes the input text to the specified file."""
        with open(self.filename, "w") as txt:
            txt.write(self.text)

    def key_salt_init(self):
        """Initializes key and salt for encryption."""
        self.passwordSalt = os.urandom(16)
        self.key = pbkdf2.PBKDF2(self.password, self.passwordSalt).read(32)
        print('AES encryption key:', binascii.hexlify(self.key))
        # Initialize IV
        self.iv = secrets.randbelow(1 << 128)  # Proper size for AES CTR mode

    def encrypt(self):
        """Encrypts the content of the file."""
        aes = pyaes.AESModeOfOperationCTR(self.key, pyaes.Counter(self.iv))
        with open(self.filename, "r") as txt:
            content = txt.read()
        ciphertext = aes.encrypt(content)
        with open(self.filename, "wb") as txt:
            txt.write(ciphertext)
        print('Encrypted:', binascii.hexlify(ciphertext))

    def decrypt(self):
        """Decrypts the content of the file."""
        aes = pyaes.AESModeOfOperationCTR(self.key, pyaes.Counter(self.iv))
        with open(self.filename, "rb") as txt:
            decrypted = aes.decrypt(txt.read())
        print('Decrypted:', decrypted.decode("utf-8"))  # Assuming the original text is UTF-8

if __name__ == "__main__":
    fileEncryption = FileEncryption()

