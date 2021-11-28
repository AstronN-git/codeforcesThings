#include <Wire.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd (0x3F, 20, 4);

#define btn 2

void setup() {
  lcd.begin();
  lcd.backlight();

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(F("Ready to start      "));
  
  pinMode(btn, INPUT);
}

int state = 0;

bool btnDown = false;

unsigned long timeStart;
unsigned long mem;

void loop() {
  if (digitalRead(btn) && !btnDown) {
    btnDown = true;
    delay(15);
    state++;
    if (state == 3) state = 0;

    if (state == 0) {
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print(F("Ready to start      "));
    }
    
    if (state == 1) {
      lcd.clear();
      timeStart = millis();
    }

    if (state == 2) {
      lcd.clear();
      mem = millis () - timeStart;
      lcd.setCursor (0, 0);
      lcd.print(F("Your time:"));
      lcd.setCursor (0, 1);
      lcd.print(mem / 1000);
      lcd.print(F(" sec "));
      lcd.print(mem % 1000);
      lcd.print(F("millis"));
    }
    
  }else if (!digitalRead(btn) && btnDown) {
    btnDown = false;
    delay(15);
  }
  printState();
}


void printState () {  
  if (state == 1) {
    lcd.setCursor(0, 0);

    unsigned long measure = millis();
    
    lcd.print((measure - timeStart) / 1000);
    lcd.print(',');
    lcd.print(((measure - timeStart) % 1000) / 100);
  }

  delay(200);
}
