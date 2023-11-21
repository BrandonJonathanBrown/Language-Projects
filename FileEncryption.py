import pyaes, pbkdf2, binascii, os, secrets
class File:

    global filename

    def __init__(self):
        print("[*] Starting Application!")

        self.File_Text()
        self.Key()
        self.Encrypt()
        self.Decrypt()


    def File_Text(self):
        self.filename = input("Please Enter A Name For The File: ")
        self.txt = open(self.filename, "w+")
        self.text = input("Please Enter Text To Write: ")
        self.txt.write(self.text)

    def Key(self):
        self.password = "34*7^%##@!"
        self.passwordSalt = os.urandom(16)
        self.key = pbkdf2.PBKDF2(self.password, self.passwordSalt).read(32)
        print('AES encryption key:', binascii.hexlify(self.key))


    def Encrypt(self):
        self.iv = secrets.randbits(256)
        self.txt = open(self.filename, "r")
        self.aes = pyaes.AESModeOfOperationCTR(self.key, pyaes.Counter(self.iv))
        self.ciphertext = ""
        if self.txt.mode == "r":
            self.content = self.txt.read()
            self.ciphertext = self.aes.encrypt(self.content)
        self.txt = open(self.filename, "wb")
        self.txt.write(self.ciphertext)
        print('Encrypted:', binascii.hexlify(self.ciphertext))

    def Decrypt(self):
        self.aes = pyaes.AESModeOfOperationCTR(self.key, pyaes.Counter(self.iv))
        self.txt = open(self.filename, "rb")
        self.decrypted = self.aes.decrypt(self.txt.read())
        print('Decrypted:', self.decrypted)


if __name__ == "__main__":
    Obj = File()

