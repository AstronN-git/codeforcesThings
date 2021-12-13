#include <Wire.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd (0x3F, 20, 4);

#define sensor A0

void setup() {
  lcd.begin();
  lcd.backlight();

  lcd.clear();
}

void loop() {
  int value = 1024 - analogRead(sensor);
  lcd.setCursor(0, 0);
  if (value > 500) {
    lcd.print("light");
  } else {
    lcd.print("dark ");
  }
  
  lcd.setCursor(0, 1);
  lcd.print(value);
}
