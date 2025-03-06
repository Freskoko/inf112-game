# Rapport – innlevering 2
**Team:** *Stein* – *Ingvild Hope, Henrik Brøgger, Guro Flatås, Gedvyde Petkeviciute*

# Prosjektrapport


Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
- Rollene fungerer bra, vi har fordelt ansvarsområder, men samarbeider fint om de ulike rollene. Per nå ser vi ikke behov for å endre rollene, da de fungerer fint som de er. 
- Team Lead: Gedvyde. Tar ansvar for å få ting til og skje, holder oversikt over fremdrift i prosjektet, avtaler møter og tar et overordnet ansvar over prosjektet. 
- Tech Lead: Henrik. Ansvarlig for backend-utvikling. Tar et overordnet ansvat for spillogikk, kollisjon og testing. 
- Front End Lead: Ingvild. Ansvar for animasjon og brukergrensesnitt. 
- Project Admin: Guro. Tar ansvar for møtereferater, passe på innleveringsfrister, sørger for dokumentasjon etc. 


Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
- I starten så var vi litt dårligere på å oppdatere issues. Startet med trello, men syntes det var litt stress å bruke enda en plattform for planlegging. Bruken ble derfor reduert som følge av det. 
- For å fikse dette endte vi opp med å bruke git issues, slik at vi har samlet alt sammen i Git. 
- Er en tilvenningssak å bli vant til å oppdatere issues. Siden vi møtes 2 ganger i uken så føler vi at vi har oversikt, men vi ser viktigheten av å ha en samlet oversikt på Git. 
- Merker at det er viktig nå som det er mange småoppgaver som må løses. Derfor er det viktig at alle på teamet har oversikt over hvilke oppgaver som må utføres, og hvem som jobber med hva. 
- Synes at dette er et litt nytt type prosjekt. Kodelogikk osv er ikke så viktig, det handler mer om å gjøre research osv. siden det er mye innebgget kode. Annen måte å jobbe med programmering. 
- Nytt at alle jobber på et prosjekt samtidig, så dette er også en tilvenningssak, men som vi føler at vi begynner å bli vant til nå. 
- Uvant å jobbe med kode som er implementert av andre, og skal komme inn og ta over og utvikle videre, kan være vanskelig. Kan være lurt med mer dokumentasjon for å unngå dette. Hvis man skal jobbe videre med andre sin kode, så er det viktig at vi hjelper hverandre med å sette igang. 


Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
- Den er fin! Alle møter opp til avtalt tid og kommuniserer godt. 
- Har møter 2 ganger i uken. 
- Eneste uenigheter vi har hatt er små ting angående kodestil og spillogikk, men de har blitt demokratisk løst på møter. 
- Vi føler det er takhøyde til å ta opp det man tenker på, noe som gjør at dynamikken i gruppen er veldig fin.


Hvordan fungerer kommunikasjonen for dere?
- Veldig bra. Vi bruker Discord veldig aktivt.

Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.
- Har klart MVC split. En utfordring å opprettholde. 
- Vi har klart å opprettholde 1-2 møter i uken. 
- Vi har klart å holde god kommunikasjon på Discord mellom møtene. 
- Vi kan bli bedre på å dokumentere og oppdatere git issue board, slik at alle teammedlemmer er oppdatert på hva som jobbes med og av hvem.
- Kan vurdere om vi skal sette frister for å få gjennomført ting. Dette  har egentlig fungert veldig fint frem til nå, og har ikke vært et behov frem til nå. Vi ser an videre i neste sprint om dette er noe vi ser på nødvendig. 

Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler)
- Har parprogrammert litt siden sist, noe som fører til litt skeivfordeling i commits. 
- Har vært en del store ting som må på plass i starten, men med flere småoppgaver etterhvert vil det nok jevne seg ut. 

Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som havner i referat).
- Referat fra møter ligger i egne .md filer

Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.
 - Bli flinkere til å oppdatere git Issue board. 
 - Skrive bedre brukerhistorier, med akseptansekriterier og arbeidsoppgaver. 
 - Dokumentere kode bedre og følge opp hverandre. 


# Krav og spesifikasjon

Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
- Vi har fått til å vise et spillebrett, der spilleren beveger seg vha tastatur og interagerer med terrenget. Vi har en foreløpig startskjerm. 
- Vi har prioritert å få på plass kollisjonsdeteksjon, slik at vi kan jobbe videre med å utvikle fiender (statiske fiender, lava og vann). 
- Vi mangler: powerups (diamanter), hvordan en spiller dør, fiender, et mål for spillebrettet, et nytt spillebrett når det er ferdig og gameover screen. 

For hvert krav dere jobber med, må dere lage 1) ordentlige brukerhistorier, 2) akseptansekriterier og 3) arbeidsoppgaver. Husk at akseptansekriterier ofte skrives mer eller mindre som tester
 

Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.
- Brukerhistorier er lagt til i git issues.

Forklar kort hvordan dere har prioritert oppgavene fremover.
- Jobbet etter MVP. Fokuserer på å få til det mest nødvendige først. 
- Jobber etter logikk, ting er avhengige av hverandre. Feks trenger fungerende kollisjonsdeteksjon for å kunne lage fiender. 
- Testing er satt litt på vent, på grunn av at prosjektet er i stadig utvikling og endres ofte.


Har dere gjort justeringer på kravene som er med i MVP? Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge utfra hva som er gitt fra kunde, hvorfor er dette gjort?
- Vi har ikke direkte fiender, har heller statiske fiender som kan drepe spiller, feks lava og vann. 

Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.
- Startet med MVP 
- Har en spiller som kan bevege seg rundt på brettet, og kollisjonsdeteksjon fungerer.
- Har laget slik at det nå er to spillere på brettet. 
- Laget en midlertidig welcomescreen. 

Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).
- Når welcomeScreen åpnes så kan man også trykke seg videre med piler. Dette skal fikses og man skal bruke museklikk for å trykke på start spillet
- Når figuren krasjer mange ganger på samme punkt vertikalt med en hindring, "spammer piltast" inn i en hindring, så tror den at den flyr. 


Kravlista er lang, men det er ikke nødvendig å levere på alle kravene hvis det ikke er realistisk. Det er viktigere at de oppgavene som er utført holder høy kvalitet. Utførte oppgaver skal være ferdige.

# Produkt og kode

Utbedring av feil: hvis dere har rettet / forbedret noe som er påpekt tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.
- Dette har vi fikset siden sist:
- kollisjonsdeteksjon
- fikset at spilleren kan bevege seg rundt på brettet. 
- kan nå plassere to spillere på brettet
- kan styre begge spillerne, piltaster for firegirl og WASD for waterboy

I README.md: Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.

Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. OBS! Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er case sensitive, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
- Vi bruker Linux, Windows og OS X, så får derfor sjekket jevnlig at prosjektet kan bygges, testes og kjøres på Linux,

Lag og lever et klassediagram. (Hvis det er veldig mange klasser, lager dere for de viktigste.) Det er ikke nødvendig å ta med alle metoder og feltvariabler med mindre dere anser dem som viktige for helheten. (Eclipse har forskjellige verktøy for dette.)
- Siden vi har stadig endring i oppsettet enda pga vi fortsatt er i startfasen så har vi ikke laget et klassediagram enda. Dette vil bli gjort til neste innlevering. 

Kodekvalitet og testdekning vektlegges. Dersom dere ikke har automatiske tester for GUI-et, lager dere manuelle tester som gruppelederne kan kjøre basert på akseptansekriteriene.
- Har ikke fokusert så mye på testing enda, måtte få på plass det grunnleggende først. Testing vil bli prioritert mere i neste sprint. 

Statiske analyseverktøy som SpotBugs eller SonarQube kan hjelpe med å finne feil dere ikke tenker på. Hvis dere prøver det, skriv en kort oppsummering av hva dere fant / om det var nyttig.

Automatiske tester skal dekke forretningslogikken i systemet (unit-tester). Coverage kan hjepe med å se hvor mye av koden som dekkes av testene – i Eclipse kan dette gjøres ved å installere EclEmma gjennom Eclipse Marketplace.

Utførte oppgaver skal være ferdige. Slett filer/kode som ikke virker eller ikke er relevant (ennå) for prosjektet. Så lenge dere har en egen git branch for innlevering, så er det ikke noe stress å fjerne ting fra / rydde den, selv om dere fortsetter utviklingen på en annen gren.