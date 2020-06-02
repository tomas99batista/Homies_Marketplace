import requests
import json
import time
from datetime import datetime
import random
import RPi.GPIO as GPIO


import sensors

def get_build_status():
    build_possible_status = ['failing', 'passing']

    build_status = ""

    response = requests.get("https://api.github.com/repos/tomas99batista/Homies_Marketplace/actions/workflows")

    json_response = json.loads(response.text)
    badge = json_response['workflows'][1]['badge_url']

    badge_response = requests.get("https://github.com/tomas99batista/Homies_Marketplace/workflows/Java%20CI%20with%20Maven/badge.svg")

    for b in build_possible_status:
        if b in badge_response.text:
            build_status = b

    return build_status

while True:
    try:
        # This can be used to tests:
        #build_possible_status = ['passing', 'failing']
        #build_status = random.choice(build_possible_status)
        
        build_status = get_build_status()
        print("BUILD_STATUS:\t", build_status.upper(), '\t', datetime.now())
        sensors.turn_led_build_status(build_status)
        time.sleep(30)
    except KeyboardInterrupt:
        GPIO.cleanup()
