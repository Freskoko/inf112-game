# Rapport – innlevering 3
**Team:** **Stein** – *Ingvild Hope, Henrik Brøgger, Guro Flatås, Gedvyde Petkeviciute*

## Prosjektrapport

Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
- Rollene fungere veldig godt. Etter forrige sprint fikk vi mer avklarte og spesifikke roller, noe som har funket veldig bra. Det har gjort at alle har fått mer definerte arbeidsroller. 
- Vi har heller ikke opplevd behov for å innføre nye roller, ettersom de eksisterende rollene dekker det som må gjøres. Vi har kun oppdatert litt på de rollene vi allerede har. 


Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
- Vi føler ikke at vi har behov for flere andre roller. 
- Team Lead: Gedvyde. Tar ansvar for å få ting til og skje, holder oversikt over fremdrift i prosjektet, avtaler møter og tar et overordnet ansvar over prosjektet. 
- Tech Lead: Henrik. Ansvarlig for backend-utvikling. Tar et overordnet ansvat for testing og git. Litt oppdatert rolle siden sist, fordi arbeidsoppgavene  har blitt litt endret.  
- Front End Lead: Ingvild. Ansvar for animasjon og brukergrensesnitt. 
- Project Admin: Guro. Tar ansvar for møtereferater, passe på innleveringsfrister, sørger for dokumentasjon etc. 


Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
- Vi synes teamet fungerer veldig bra, både sosialt og faglig. Det er lav terskel for å stille spørsmål, be om hjelp eller diskutere ideer og fremgangsmåter. 
- Etter forrige sprint så innførte vi deadlines. Sånn at vi satte en frist for de ulike issuene og tingene som måtte gjøres. Dette er noe vi syntes var positivt med tanke på arbeidsflyt og fremgang i prosjektet. 

Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
- Gruppedynamikken er fortsatt veldig fin!
- Vi har ikke hatt noen uenigheter innad i gruppen. 
- Er det noe vi er uenige i så diskuterer vi det med en gang og kommer frem til en løsning sammen. 

Hvordan fungerer kommunikasjonen for dere?
- Kommunikasjon fungerer supert. 
- Vi bruker Discord som hovedkanal for kommunikasjon. 
- Der har vi en egen channel for ulike temaer, Feks: MeetingPlans, MergeRequests, General, Deadlines, Graphics. 
- Vi synes dette er veldig oversiktlig og samler all informasjon på ett sted. 
- Hver gang vi lager en merge request så sender vi den i Discord, så reagerer vi med en tankeboble om vi har noen spørsmål. Hvis vi approver MR så reagerer vi med en grønn checkmark. 


Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.
- Vi har klart å vært konsistente ved å opprettholde faste møter hver mandag og torsdag. Det har vært unntak hvor vi har avlyst møter fordi mange har vært bortreist etc. 
- Vi har klart å opprettholde motivasjon og jevn jobbing gjennom hele perioden. 
- Vi har klart å ha et fint og motiverende arbeidsmiljø, hvor alle hjelper og bistår hverandre. 

Forbedringer:
- Vi kunne ha delt opp oppgavene i mindre tasks, for å gjøre det lettere å komme i gang og fordele arbeid jevnere.
- Noen ganger så har vi samlet mange oppgaver i en issue, som gjør at det tar lang tid og gjennomføre og blir en stor merge request når den skal merges til main. Derfor kan det være lurt å dele opp i mindre issues slik at vi kan pushe oftere til main og unngå merge conflicter. Dette vil også kanskje føre til en jevnere fremgang i prosjektet. 
- Vi kan bli flinkere til å lage tydelige mål for hva som skal være ferdig innen neste sprint, slik at vi har god oversikt og tydelige mål og forfallsdatoer å jobbe mot. 
- Vi kunne hatt bedre og mer grundige merge request reviews. 

Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler)
- Det er litt forskjell i antall commits, men det er hovedsaklig fordi noen av oss committer oftere små endringer enn andre. 


Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som havner i referat).
- Referat ligger under doc/meeting reports. 

Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.
- Forbedringspunkter til neste sprint:
1. Dele opp arbeidsoppgaver i mindre tasks. 
2. Lage tydelige mål for hva vi ønsker å oppnå iløpet av hver sprint, slik at det blir enklere å jobbe jevnt. 
3. Ha grundigere merge request reviews. 


## Krav og spesifikasjon


Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
- Ja, vi er forbi MVP. Spillet fungerer som det skal krav til MVP er dekt. Vi har lagt inn alle features vi ønsker, og det gjenstår nå kun finpuss og oppdateringer. 
- Vi har prioritert modulær kode. 
- Vi har opprettet factory. 
- Har lagt til bakgrunnsmusikk og lyd når en spiller dør. 
- Har lagt til powerup, en hvit diamant som begge spillere kan fange som gir et ekstra liv. 
- Vi har nå mye høyere test coverage. 
- Har lagt til poeng når en spiller fanger en diamant. 


For hvert krav dere jobber med, må dere lage 1: ordentlige brukerhistorier, 2: akseptansekriterier og 3: arbeidsoppgaver. Husk at akseptansekriterier ofte skrives mer eller mindre som tester
- Brukerhistorier, akseptansekrav og arbeidsoppgaver ligger under "doc/brukerhistorier.md".

Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.
- Brukerhistorier, akseptansekrav og arbeidsoppgaver ligger under "doc/brukerhistorier.md".

Forklar kort hvordan dere har prioritert oppgavene fremover:
- Vi har prioritert alt av spillogikk foran design og grafikk. 
- Når vi har alt av logikk osv som fungerer, så vil vi fokusere mer på grafikk. 
- Flere småting og finpuss som må gjøres før siste innlevering. 
- Lage interface for public metoder, fikse opp i javadocs og opprydning. 

Har dere gjort justeringer på kravene som er med i MVP? Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge utfra hva som er gitt fra kunde, hvorfor er dette gjort?
- Vi er forbi MVP, vi har dekket alle de kravene. 
- Det ble gjort små endringer i rekkefølgen med tanke på hva som var logisk å implementere med tanke på hvordan vi har valgt å implementere tidligere kode. 


Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.

Siden forrige gang:
- Implementert bevegende plattformer
- Implementert diamanter man kan samle
- Spillet har nå mål og flere maps. 
- Lagt til powerUp diamant
- Lagt til musikk. 
- Lagt til flere tester. 
- Fikset bugs med contactListener slik at bug med hopping til spilleren er borte. 
- Har lagt til Texture til kroppene, og animasjon på kroppene til figuerene og midlertigig hode Texture.


Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).
- Å bli presset mot bakken av en plattform gjør at spilleren blir stuck i bakken. 

Kravlista er lang, men det er ikke nødvendig å levere på alle kravene hvis det ikke er realistisk. Det er viktigere at de oppgavene som er utført holder høy kvalitet. Utførte oppgaver skal være ferdige.


## Produkt og kode
(Evt. tekst / kommentarer til koden kan dere putte i en egen ## Kode-seksjon i doc/obligX.md.)


Utbedring av feil: hvis dere har rettet / forbedret noe som er påpekt tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.

Dette har vi fikset siden sist:
- Vi har laget en MapsFactory. Under CodeReview med Anya så godkjente hun at dette dekker kravene for fabrikk som er beskrevet i oppgaveteksten. 
- Vi har lagt til en powerUp diamond, som gir spilleren et ekstra liv. 
- Vi har nå betraktelig høyere test coverage. 
- Vi har lagt til musikk. 
- Lagd klassediagram. 
- Oppdatert figurer. 

I README.md: Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.

Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. OBS! Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er case sensitive, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
- Innad i gruppen så bruker vi både Mac, Windows og Linux slik at vi hele tiden får sjekket at prosjektet kan bygges, testes og kjøres på alle tre. 

Lag og lever et klassediagram. (Hvis det er veldig mange klasser, lager dere for de viktigste.) Det er ikke nødvendig å ta med alle metoder og feltvariabler med mindre dere anser dem som viktige for helheten. (Eclipse har forskjellige verktøy for dette.)
- Klassediagram ligger under doc/classDiagram. Ligger et stort for hele main, og egne for model, view, controller og app. Siden ko

Kodekvalitet og testdekning vektlegges. Dersom dere ikke har automatiske tester for GUI-et, lager dere manuelle tester som gruppelederne kan kjøre basert på akseptansekriteriene.

Statiske analyseverktøy som SpotBugs eller SonarQube kan hjelpe med å finne feil dere ikke tenker på. Hvis dere prøver det, skriv en kort oppsummering av hva dere fant / om det var nyttig.

Automatiske tester skal dekke forretningslogikken i systemet (unit-tester). Coverage kan hjepe med å se hvor mye av koden som dekkes av testene – i Eclipse kan dette gjøres ved å installere EclEmma gjennom Eclipse Marketplace.

Utførte oppgaver skal være ferdige. Slett filer/kode som ikke virker eller ikke er relevant (ennå) for prosjektet. Så lenge dere har en egen git branch for innlevering, så er det ikke noe stress å fjerne ting fra / rydde den, selv om dere fortsetter utviklingen på en annen gren.





