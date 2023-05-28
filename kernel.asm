org 0
bits 16

_.start:
push cs
pop  ds
mov  si, msg
jmp Print

GameLoop:
mov ah, 00h
int 1ah
mov  ax, dx
xor  dx, dx
mov  cx, 10
div  cx
add  dl, '0'
mov  ah, 0x00
int  16h
mov  ah, 0x0E
int  0x10

GameCondition:
mov  si, won
cmp  al, dl
je   GameWon
jmp  GameLost

GameLost:
mov  si, loss
jmp Print

GameWon:
jmp Print

Print:
jmp  BeginLoop

PrintLoop:
mov  ah, 0x0E
int  0x10

BeginLoop:
mov  al, [si]
inc  si
or al, al
jz GameLoop
test al, al
jnz  PrintLoop
ret

; -------------------
msg db   'Welcome To My Guessing Game: ', 10, 13, 'Pick A Number Between 0 - 9 ', 10, 13, 0
won db   'You Guessed The Correct Number!', 10, 13, 0
loss db  'You Guessed The Incorrect Number!', 10, 13,  0

times 512 - ($-$$) db 0
