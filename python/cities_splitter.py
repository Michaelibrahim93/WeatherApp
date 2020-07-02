
import json
with open(r".\city.list.json", encoding="utf8") as infile:
  o = json.load(infile)
  chunkSize = 1000
  for i in range(0, len(o), chunkSize):
    with open(r".\cities\city_list_" + str(i//chunkSize) + r".json", r"w") as outfile:
      json.dump(o[i:i+chunkSize], outfile)