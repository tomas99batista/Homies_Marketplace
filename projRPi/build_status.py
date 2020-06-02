import requests
import json

build_possible_status = ['passing', 'failing']

build_status = ""

response = requests.get("https://api.github.com/repos/tomas99batista/Homies_Marketplace/actions/workflows")

json_response = json.loads(response.text)
badge = json_response['workflows'][1]['badge_url']

badge_response = requests.get("https://github.com/tomas99batista/Homies_Marketplace/workflows/Java%20CI%20with%20Maven/badge.svg")

for b in build_possible_status:
    if b in badge_response.text:
        build_status = b

