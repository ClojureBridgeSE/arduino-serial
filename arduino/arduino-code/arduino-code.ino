#include <ADCTouch.h>

int ref1, ref2, ref3, ref4, ref5, ref6;     //reference values to remove offset
int ledPin1 = 3;
int ledPin2 = 5;
int ledPin3 = 6;
int ledPin4 = 9;
int ledPin5 = 11;
int ledPin6 = 10;

void setup() 
{
    // No pins to setup, pins can still be used regularly, although it will affect readings

    Serial.begin(9600);

    ref1 = ADCTouch.read(A3, 500);    //account for the capacitance of the pad
    ref2 = ADCTouch.read(A2, 500);    //account for the capacitance of the pad
    ref3 = ADCTouch.read(A1, 500);    //account for the capacitance of the pad
    ref4 = ADCTouch.read(A0, 500);    //account for the capacitance of the pad
    ref5 = ADCTouch.read(A4, 500);    //account for the capacitance of the pad
    ref6 = ADCTouch.read(A5, 500);    //account for the capacitance of the pad
    

} 

void loop() 
{
    int value1 = ADCTouch.read(A3);   //   --> 100 samples
    int value2 = ADCTouch.read(A2);   //   --> 100 samples
    int value3 = ADCTouch.read(A1);   //   --> 100 samples
    int value4 = ADCTouch.read(A0);   //   --> 100 samples
    int value5 = ADCTouch.read(A4);   //   --> 100 samples
    int value6 = ADCTouch.read(A5);   //   --> 100 samples

    value1 -= ref1;       //remove offset
    value2 -= ref2;
    value3 -= ref3;
    value4 -= ref4;
    value5 -= ref5;
    value6 -= ref6;
    
    if(value1 < 0){value1=0;}
    if(value2 < 0){value2=0;}
    if(value3 < 0){value3=0;}
    if(value4 < 0){value4=0;}
    if(value5 < 0){value5=0;}
    if(value6 < 0){value6=0;}
    
    analogWrite(ledPin1, value1);
    analogWrite(ledPin2, value2);
    analogWrite(ledPin3, value3);
    analogWrite(ledPin4, value4);
    analogWrite(ledPin5, value5);
    analogWrite(ledPin6, value6);

    Serial.print(value1);
    Serial.print("\t");
    Serial.print(value2);
    Serial.print("\t");
    Serial.print(value3);
    Serial.print("\t");
    Serial.print(value4);
    Serial.print("\t");
    Serial.print(value5);
    Serial.print("\t");
    Serial.println(value6);
    delay(50);
}
