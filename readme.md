### Curl request example

Kräver att Å/Ä/Ö encodas manuellt - Applikationens controller säger ifrån annars: "Invalid character found in the request target". 

Försökt åtgärda i src/main/resources/application.properties men får det ej att fungera.

```bash
curl 'http://localhost:8080/api/meanChurchFee?county=V%C3%84RMD%C3%96&fromYear=2017&toYear=2023'

```

