{
 "errobj": null,
 "response": {
  "url": "http://media.playhaven.com/content-templates/f3f6d4dab39a187288c98079e8455985de9f8c04/html/image.html.gz",
  "frame": {
   "PH_LANDSCAPE": {
    "y": 0,
    "h": 480,
    "w": 548,
    "x": 126
   },
   "PH_PORTRAIT": {
    "y": 190,
    "h": 420,
    "w": 480,
    "x": 0
   }
  },
  "transition": "PH_DIALOG",
  "close_ping": "http://api2.playhaven.com/v3/publisher/dismiss?ordinal=0&nonce=2684639463&hardware=GT-I9100&placement_tag=launch&game_id=42714&app_os=android&ct=2&content_id=1254194&opt_out=0&resolution=dismiss&device_token=1bc14fdb92cf95aa&sdk_version=1.12.3&ts=1382200831.55&languages=fr&connection=wifi&signature=94a58a314be371d2c89ddc2bb42ead696dc6dd07&td=0&app_version=1.9&os=4.1.2+16",
  "context": {
   "content": {
    "touch_dispatch": {
     "type": "launch",
     "parameters": {
      "url": "http://api2.playhaven.com/v3/publisher/dismiss?ordinal=0&nonce=1429567594&hardware=GT-I9100&placement_tag=launch&ad_game_id=71682&game_id=42714&app_os=android&ct=2&content_id=1254194&opt_out=0&resolution=buy&device_token=1bc14fdb92cf95aa&sdk_version=1.12.3&ts=1382200831.55&languages=fr&impression_type=internal&connection=wifi&signature=45624d4598ed4f6b6e867ec31019941efa01ec34&td=0&app_version=1.9&os=4.1.2+16"
     }
    },
    "image": {
     "PH_LANDSCAPE": {
      "url": "http://media.playhaven.com/content-images/42714/131015085332/PLAYHEAVEN-640.png",
      "h": 373,
      "w": 426
     },
     "PH_PORTRAIT": {
      "url": "http://media.playhaven.com/content-images/42714/131015085332/PLAYHEAVEN-640.png",
      "h": 373,
      "w": 426
     }
    },
    "type": "image",
    "dismiss_dispatch": {
     "type": "dismiss",
     "parameters": {
      "ping": "http://api2.playhaven.com/v3/publisher/dismiss?ordinal=0&nonce=0844966950&hardware=GT-I9100&placement_tag=launch&game_id=42714&app_os=android&ct=2&content_id=1254194&opt_out=0&resolution=dismiss&device_token=1bc14fdb92cf95aa&sdk_version=1.12.3&ts=1382200831.56&languages=fr&connection=wifi&signature=4a03bd0286d461ae50f7ae7fa1e256dbbf5f1d44&td=0&app_version=1.9&os=4.1.2+16"
     }
    },
    "complete_dispatch": {
     "type": "track",
     "parameters": {
      "url": "http://api2.playhaven.com/v3/impression?ordinal=0&nonce=4041448338&hardware=GT-I9100&placement_tag=launch&game_id=42714&app_os=android&ads=%5B%7B%22ordinal%22%3A+0%2C+%22game_id%22%3A+71682%2C+%22creative_id%22%3A+null%2C+%22campaign_id%22%3A+null%7D%5D&content_id=1254194&opt_out=0&resolution=client_impression&device_token=1bc14fdb92cf95aa&sdk_version=1.12.3&ts=1382200831.56&languages=fr&connection=wifi&signature=aa3dfca6953950a1781602bfd57dc8b5e459ec1c&ct=2&td=0&app_version=1.9&os=4.1.2+16"
     }
    }
   },
   "clickable_level": "creative",
   "style": {},
   "localization": {
    "fine_print_link_text": "Privacy Policy",
    "fine_print_text2": ".",
    "fine_print_text1": "By submitting this information, you confirm that you are over the age of 13 and agree to the",
    "free_text": "FREE",
    "text_8": "HUIT",
    "text_9": "NEUF",
    "text_6": "SIX",
    "text_7": "SEPT",
    "text_4": "QUATRE",
    "text_5": "CINQ",
    "text_2": "DEUX",
    "text_3": "TROIS",
    "text_0": "Z\u00c9RO",
    "text_1": "UN",
    "lock_error": "Incorrect, merci de r\u00e9essayer",
    "buy_button_text": "GET IT",
    "list_header_text": "Players of this game also like\u2026",
    "lock_instructions": "Merci de saisir le code d'acc\u00e8s",
    "back_button_text": "Back",
    "dismiss_button_text": "Done",
    "currency_symbol": "$",
    "lock_text": "CONTR\u00d4LE PARENTAL",
    "skip_button_text": "No Thanks"
   }
  },
  "close_delay": 5
 },
 "error": null
}