# Brukerhistorier

## Mal
**Brukerhistorier:**

**Akseptansekriterer:**

**Arbeidsoppgave:**

# UI brukerhistorier

## HUD

**Brukerhistorier:**
- Som spiller ønsker jeg å se et HeadsUpDisplay i toppen av skjermen som inneholder en timer og poeng. 

**Akseptansekriterer:**
- Spilleren ser en HUD på toppen av activeGameScreen.

**Arbeidsoppgave:**
- Implementere HUD
- Oppdatere Score når spillerene fanger diamanter. 

## Velkomstskjerm

**Brukerhistorier:**
-	Som en ny bruker ønsker jeg å se en velkomstskjerm når jeg åpner appen slik at jeg forstår hva appen handler om og enkelt kan navigere videre til spillstart og help-skjerm. 


**Akseptansekriterer:**
-	Brukeren ser en WelcomeScreen når appen startes.
-	Spillerene kan velge om de vil være firegirl eller water boy, og den andre spilleren må velge motsatt. 
-	Når spilleren trykker "Start", går de videre til ChooseMapScreen, en skjerm hvor de velger level.

**Arbeidsoppgaver:**
-	Implementere WelcomeScreen som en klasse som implementere screen. 
-	Implementere UI-elementer for spillervalg og knapper. 
-	Implementere funksjonalitet for å registrere spillerens valg. 

## ChooseMap skjerm

**Brukerhistorier:**
-	Som bruker ønsker jeg å velge hvilken level jeg vil spille før spillet starter og se hvor mange levler jeg har fullført.

**Akseptansekriterer:**
-	Etter brukeren har valgt spillere i WelcomeScreen og trykket «start» så kommer de til ChooseMap screen. Der kan de velge map.
-	Bruker ser en liste med maps de kan velge. 
-	Må ha fullført map 1 for og ta map 2 osv. 

**Arbeidsoppgaver:**
-	Lage en egen klasse ChooseMapScreen.
-	Lage knapper for å velge bane. 
-	Koble nivåvalg til spillogikk. 

## Hjelp skjerm

**Brukerhistorie:**
-	Som bruker ønsker jeg å kunne trykke på en help knapp når jeg er i velkomstskjermen og komme til en skjerm der det står forklart hvordan spillet fungerer.

**Akseptansekriterer:**
-	Brukeren kan navigere til HelpScreen via en knapp i WelcomeScreen. 
-	HelpScreen inneholder hvordan spillet fungerer, regler og mål for spillet. 
-	Kan navigere tilbake til WelcomeScreen. 

**Arbeidsoppgave:**
-	Lage HelpScreen klasse. 
-	Lage en knapp på WelcomeScreen som tar brukeren til HelpScreen. 
-	Legge til tekst som forklarer spillet. 
-	Lage en knapp som tar bruker tilbake til WelcomeScreen. 

## Game over skjerm

**Brukerhistorie:**
-	Som en spiller ønsker jeg å se en Game Over-skjerm når jeg mister alle liv eller feiler et nivå slik at jeg kan velge om jeg vil prøve igjen eller gå tilbake til hovedmenyen.

**Akseptansekriterier:**
-	Brukeren ser en game over screen når de dør.
-	Spiller kan velge å restarte level eller tilbake til ChooseMapsScreen.


**Arbeidsoppgaver:**
-	Lage en GameOverScreen-klasse som implementerer Screen.
-	Implementere logikk for å restarte nivået eller gå til hovedmenyen.


# Spillogikk brukerhistorier

## Lydeffekt
**Brukerhistorier:**
- Som spiller ønsker jeg å høre en lydeffekt når karakteren min dør. 

**Akseptansekriterer:**
- En passende lydeffekt spilles av umiddelbart når en spiller dør.

**Arbeidsoppgave:**
- Finne eller lage en passende lydeffekt for spillerdød.
- Implementere funksjonalitet for å trigge lyden når en spiller dør.


## Bevegende plattform

**Brukerhistorier:**
- Som spiller ønsker jeg å kunne hoppe på en plattform som beveger seg, slik at jeg kan komme meg høyere opp og nå ellers utilgjengelige områder. 

**Akseptansekriterer:**
- Bevegelige plattformer skal være implementert i spillet.
- Spilleren skal kunne stå på plattformen mens den beveger seg.
- Plattformen skal bevege seg jevnt innenfor definerte grenser uten å krasje i vegger eller andre objekter.

**Arbeidsoppgave:**
- Implementere bevegelsesmønster for plattformene (f.eks. horisontal eller vertikal bevegelse).
- Sørge for at plattformer stopper eller snur når de møter hindringer.
- Håndtere kollisjon mellom plattformer og vegger.
- Sørge for at spilleren følger plattformens bevegelse mens vedkommende står på den. 

## Kollisjon 

**Brukerhistorie:**
-	Som en spiller trenger jeg at karakteren min stopper når den treffer en vegg eller et hinder. 

**Akseptansekriterier:**
-	Karakteren skal ikke kunne bevege seg gjennom vegger. 
-	Karakteren skal kunne stå på plattformer uten å falle gjennom. 
-	Hvis karakteren kolliderer med en fiende (vann eller lava), så dør den. 

**Arbeidsoppgave:**
-	Implementere kollisjonssystem mellom karakterer, hindringer og omgivelser 


## Bevegelse: 
**Brukerhistorie:** 
-	Som en spiller trenger jeg å kunne bevege en karakter for å navigere spillverden. 

**Akseptansekriterier:**
-	Karakteren må kunne bevege seg til venstre, høyre og hoppe. 
-	Karakteren må kunne hoppe over hindringer. 

**Arbeidsoppgave:**
-	Implementere bevegelseskontroller.  


## Samle diamanter:

**Brukerhistorie:**
-	Som spiller ønsker jeg å fange diamanter ved å interagere med dem slik at jeg kan få poeng eller andre fordeler.

**Akseptansekriterer:**
-	Skal kunne “fange” diamanter ved å interagere med dem.
-	Diamanten skal forsvinne fra brettet når spilleren har fanget den.
-	Spilleren skal få poeng eller powerup når den fanger en diamant.

**Arbeidsoppgave:**
-	Legg til diamanter som en entity
-	Implementer metodikk slik at det registreres når en spiller interagerer med en diamant.
-	Legg til en effekt ved samling (f.eks. økning i poengsum, midlertidig boost, ekstra liv)

## Powerups

**Brukerhistorier:**
- Som spiller vil jeg få powerup når jeg samler inn en diamant med en spesiell egenskap. 

**Akseptansekriterer:**
- Å samle en diamant gir en fordel, feks at man blir immun mot fiender. 

**Arbeidsoppgave:**
- Implementere immunitet mot fiender. 


## Game Over

**Brukerhistorier:**
- Som spiller, når jeg dør, ønsker jeg at spillet avsluttes riktig slik at jeg ikke kan fortsette å spille, og at jeg kan få valget om å starte på nytt eller gå tilbake til hovedmenyen.

**Akseptansekriterer:**
- Spillet registrerer korrekt når spilleren dør. 
- Når spilleren dør, skal all spillaktivitet stanses (feks. fiender, tid, input).
- Spillet skal gå over i en "Game Over"-tilstand, og gi mulighet for å starte nivået på nytt eller gå tilbake til hovedmenyen.

**Arbeidsoppgave:**
- Implementere logikk for å oppdage når spilleren dør.
- Stoppe all aktiv spill-logikk ved død (inkludert bevegelse, input og oppdatering av fiender).
- Sørge for at spilleren ikke kan utføre input eller påvirke spillet etter død.
- Koble til UI-funksjonalitet (f.eks. overgang til GameOverScreen, restart, hovedmeny). 


## Fiender
**Brukerhistorier:**
-	Som spiller så dør jeg når jeg kommer i kontakt med en fiende, som er vann eller lava.  

**Akseptansekriterer:**
- Firegirl dør i kontakt med vann. 
- Waterboy dør i kontakt med lava. 
- Spille av en lyd når en spiller dør. 

**Arbeidsoppgave:**
- Implementere fiendeobjekter (lava og vann) i spillet. 
- Legge til logikk som sjekker hvilken karakter som er i kontakt med hvilken fiende.
- Legge til lyd når en spiller dør. 


## Samle poeng:
**Brukerhistorier:**
- Som spiller ønsker jeg å kunne samle poenggjenstander, slik at jeg kan få høyere poengsum. 

**Akseptansekriterer:**
- Poenggjenstander (diamanter) plasseres på brettet.
- Spilleren kan plukke dem opp.
- Poengsum oppdateres og vises tydelig. 

**Arbeidsoppgave:**
- Legge til poenggjenstander i kartet.
- Oppdatere kollisjonslogikk. 


## Mål for brettet

**Brukerhistorier:**
- Som spiller ønsker jeg å nå et målpunkt på brettet, slik at jeg kan fullføre nivået og gå videre.

**Akseptansekriterer:**
- Spilleren kan nå et synlig mål, som ser ut som en dør. 
- Når målpunktet nås er spillet vunnet. 

**Arbeidsoppgave:**
- Lage målobjekt som kan detekteres.
- Sjekk kollisjon med spiller.
- Implementere overgang til en skjerm/vindu som viser at spillet er beseiret og evt poengscore. 






