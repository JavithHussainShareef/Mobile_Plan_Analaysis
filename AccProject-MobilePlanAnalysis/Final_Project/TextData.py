# Import necessary modules
import json
import re

# Define the JSON-like structure
data = {
"Rogers": [
    {
      "planType": "5G",
      "planName": "5G Infinite Lite - Bring Your Own Device",
      "monthlyCost": "$39.00 per mo±",
      "dataAllowance": "50 GB at speeds up to 250 Mbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Lite - Finance Your Device",
      "monthlyCost": "$49.00 per mo±",
      "dataAllowance": "50 GB at speeds up to 250 Mbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Essential - Bring Your Own Device",
      "monthlyCost": "$50.00 per mo±",
      "dataAllowance": "75 GB 100 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Essential - Finance Your Device",
      "monthlyCost": "$60.00 per mo±",
      "dataAllowance": "100 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Extra - Bring Your Own Device",
      "monthlyCost": "$65.00 per mo±",
      "dataAllowance": "150 GB 175 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4, 1000 International Long Distance minutes to select countries16"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Extra - Finance Your Device",
      "monthlyCost": "$75.00 per mo±",
      "dataAllowance": "175 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited Canada-wide talk and text1, Unlimited international messaging from Canada4, 1000 International Long Distance minutes to select countries16"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Premium - Bring Your Own Device",
      "monthlyCost": "$85.00 per mo±",
      "dataAllowance": "175 GB 200 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited international messaging from Canada4, 1000 International Long Distance minutes to select countries18"
    },
    {
      "planType": "5G",
      "planName": "5G Infinite Premium - Finance Your Device",
      "monthlyCost": "$95.00 per mo±",
      "dataAllowance": "200 GB at speeds up to 1 Gbps. Unlimited data at reduced speeds thereafter*",
      "networkCoverage": "Access to Rogers 5G/5G+ network",
      "callAndTextAllowance": "Unlimited international messaging from Canada4, 1000 International Long Distance minutes to select countries18"
    }
  ],
  "Bell": [
    {
      "planType": "5G+",
      "planName": "Ultimate 100",
      "monthlyCost": "$75.00/mo.",
      "dataAllowance": "100 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada. Unlimited international texting"
    },
    {
      "planType": "5G+",
      "planName": "Ultimate 175",
      "monthlyCost": "$90.00/mo.",
      "dataAllowance": "175 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada. Unlimited international texting"
    },
    {
      "planType": "5G+",
      "planName": "Ultimate 200 - CAN/U.S./MEX",
      "monthlyCost": "$110.00/mo.",
      "dataAllowance": "200 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada, the U.S., and Mexico. Unlimited international texting"
    },
    {
      "planType": "5G+",
      "planName": "Ultimate 100",
      "monthlyCost": "$60.00/mo.",
      "dataAllowance": "100 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada. Unlimited international texting"
    },
    {
      "planType": "5G+",
      "planName": "Ultimate 175",
      "monthlyCost": "$70.00/mo.",
      "dataAllowance": "175 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada. Unlimited international texting"
    },
    {
      "planType": "5G+",
      "planName": "Ultimate 200 - CAN/U.S./MEX",
      "monthlyCost": "$90.00/mo.",
      "dataAllowance": "200 GB at fastest available 5G+ speeds. Unlimited data at up to 512 Kbps thereafter",
      "networkCoverage": "5G+ network access",
      "callAndTextAllowance": "Unlimited calling, texting & data in Canada, the U.S., and Mexico. Unlimited international texting"
    }
  ],
  "Freedom": [
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico 50GB",
      "monthlyCost": "$35/mo.",
      "dataAllowance": "50GB data. Unlimited data at reduced speeds thereafter",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "Unlimited talk & text"
    },
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico + Roam Beyond 75GB",
      "monthlyCost": "$45/mo.",
      "dataAllowance": "75GB data. Unlimited data at reduced speeds thereafter",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "Unlimited talk & text",
      "additionalFeatures": "10GB Roam Beyond data in 100+ destinations"
    },
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico + Roam Beyond 100GB",
      "monthlyCost": "$55/mo.",
      "dataAllowance": "100GB data. Unlimited data at reduced speeds thereafter",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "Unlimited talk & text",
      "additionalFeatures": "20GB Roam Beyond data in 100+ destinations"
    },
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico 100MB",
      "monthlyCost": "$5/mo.",
      "dataAllowance": "100MB data",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "2 hours talk, Unlimited text"
    },
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico 3GB",
      "monthlyCost": "$20/mo.",
      "dataAllowance": "3GB data. Unlimited data at reduced speeds thereafter",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "2 hours talk, Unlimited text"
    },
    {
      "planType": "5G",
      "planName": "Canada-U.S.-Mexico 10GB",
      "monthlyCost": "$29/mo.",
      "dataAllowance": "10GB data. Unlimited data at reduced speeds thereafter",
      "networkCoverage": "Canada + U.S. + Mexico",
      "callAndTextAllowance": "Unlimited talk & text"
    }
  ],
  "Koodo": [
    {
      "planType": "4G",
      "planName": "Basic Unlimited Plan",
      "monthlyCost": "$20.00/mo.",
      "dataAllowance": "No data included",
      "networkCoverage": "Unlimited Canada-wide minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging ,US and International Easy Roam, Call Display+, Voicemail, Call Waiting, Conference Calling"
    },
    {
      "planType": "4G",
      "planName": "Shock-Free 10GB Plan",
      "monthlyCost": "$34.00/mo.",
      "dataAllowance": "10GB of Shock-Free data at 4G speed",
      "networkCoverage": "Unlimited Canada-wide minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging, Pick 1 FREE Perk, US and International Easy Roam, Call Display+, Voicemail, Call Waiting, Conference Calling"
    },
    {
      "planType": "4G",
      "planName": "Shock-Free 50GB Plan",
      "monthlyCost": "$39.00/mo.",
      "dataAllowance": "50GB of Shock-Free data at 4G speed",
      "networkCoverage": "Unlimited Canada-wide minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging (text & picture)"
    },
    {
      "planType": "3G",
      "planName": "Starter 250MB Plan",
      "monthlyCost": "$15.00/mo.",
      "dataAllowance": "250MB of Shock-Free data at 3G speed",
      "networkCoverage": "100 Canada-wide minutes with unlimited incoming minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging (text only), US and International Easy Roam, Call Display+, Voicemail, Call Waiting, Conference Calling"
    },
    {
      "planType": "3G",
      "planName": "Shock-Free 3GB Plan",
      "monthlyCost": "$35.00/mo.",
      "dataAllowance": "3GB of Shock-Free data at 3G speed",
      "networkCoverage": "Unlimited Canada-wide minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging (text & picture)"
    },
    {
      "planType": "4G",
      "planName": "Shock-Free 2GB Plan",
      "monthlyCost": "$37.50/mo.",
      "dataAllowance": "2GB of Shock-Free data at 4G speed",
      "networkCoverage": "Unlimited Canada-wide minutes",
      "callAndTextAllowance": "Unlimited Canada-wide messaging (text & picture)"
    }
  ],
"Telus": [
    {
      "planType": "5G+",
      "planName": "Premium Unlimited Canada-US-Mexico",
      "monthlyCost": "$95/$90 per month",
      "dataAllowance": "200GB shareable data at speeds up to 2Gbps and unlimited data at reduced speed",
      "networkCoverage": "Unlimited data, talk and text in Canada, the US and Mexico",
      "callAndTextAllowance": "Unlimited international messaging"
    },
    {
      "planType": "5G+",
      "planName": "Premium Unlimited",
      "monthlyCost": "$75/$70 per month",
      "dataAllowance": "175GB shareable data at speeds up to 2Gbps and unlimited data at reduced speed",
      "networkCoverage": "Unlimited nationwide data, talk and text",
      "callAndTextAllowance": "Unlimited international messaging"
    },
    {
      "planType": "5G+",
      "planName": "Essential Unlimited",
      "monthlyCost": "$65/$60 per month",
      "dataAllowance": "100GB shareable data at speeds up to 2Gbps and unlimited data at reduced speed",
      "networkCoverage": "Unlimited nationwide data, talk and text",
      "callAndTextAllowance": "Unlimited international messaging"
    },
    {
      "planType": "Voice",
      "planName": "Voice 25",
      "monthlyCost": "$25/$20 per month",
      "dataAllowance": "Pay-per-use data",
      "networkCoverage": "Unlimited nationwide talk & text",
      "callAndTextAllowance": "Unlimited nationwide talk & text"
    }
  ]
}


# Extract unique words from the JSON-like structure
unique_words = set()
for carrier, plans in data.items():
    for plan in plans:
        for key, value in plan.items():
            # Extract words using regex
            words = re.findall(r'\b\w+\b', str(value))
            unique_words.update(words)

# Write all extracted words to a text file
output_file_path = "C:\\Users\\chira\\Desktop\\JavaCodes\\Final_Project\\UniqueData.txt"
with open(output_file_path, "w") as file:
    file.write("\n".join(sorted(unique_words)))

print(f"Dictionary file created at: {output_file_path}")
