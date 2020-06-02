import RPi.GPIO as GPIO
import time


GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# --- SETUP ---
# LEDS
GREEN_LED = 20
RED_LED = 21
GPIO.setup(GREEN_LED,GPIO.OUT)
GPIO.setup(RED_LED,GPIO.OUT)
# BUZZER
BUZZER_PIN = 26   # pin11
GPIO.setup(BUZZER_PIN, GPIO.OUT)


def turn_led_build_status(led):
    if led == 'failing':
        GPIO.output(GREEN_LED, GPIO.LOW)
        GPIO.output(RED_LED,GPIO.HIGH)
        play_buzzer()
        
    elif led == 'passing':
        GPIO.output(RED_LED, GPIO.LOW)
        GPIO.output(GREEN_LED,GPIO.HIGH)

def play_buzzer():
    for i in range(5):
        GPIO.output(BUZZER_PIN, GPIO.HIGH)
        time.sleep(0.5)
        GPIO.output(BUZZER_PIN, GPIO.LOW)
        time.sleep(0.5)
