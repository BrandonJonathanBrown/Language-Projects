#!/bin/bash
nasm -f bin bootload.asm -o bootload.bin
nasm -f bin kernel.asm -o kernel.bin
cat bootload.bin kernel.bin > os.bin
sudo dd if=os.bin of=bootsec.flp

#qemu-system-x86_64 -drive format=raw,file=disk.img
#nasm -f bin boot.asm -o disk.img
sudo qemu-system-x86_64 -fda bootsec.flp

#dcfldd if=masi.img of=/dev/disk2 of=/dev/disk3 of=/dev/disk4
