# Rapport – innlevering 1
**Team:** *Stein* – *Ingvild Hope, Henrik Brøgger, Guro Flatås, Gedvyde Petkeviciute*

## A0

**Kompetanse:**
* `Ingvild Hope`: INF100, INF101, INF102, INF140, INF161, Gruppeleder: INF100, INF101
* `Henrik Brøgger`: INF100, INF101, INF102, INF161, INF264, Gruppeleder: INF100, INF161, Utnevnt Git-ekspert
* `Guro Flatås`: INFO132, INF101, INFO135, INF140, INF161
* `Gedvyde Petkeviciute`:  INF100, INF101, INF102, INF140, INF161, Gruppeleder: INF100

## A1

Repo er opprettet alt er fikset.

## A2:

To spillfigurer som kan styres, gå til høyre/venstre, hoppe oppover.

Todimensjonal verden:

  * Bakke / platform spillerne kan stå på.
  * Vegg som spilleren ikke kan gå gjennom
  * Spilleren må komme seg til slutten av spillebrettet *mål*
  * Spillerne kan få "poeng" ved å plukke opp ting (ex: diamanter)
  * Utfordringen i spillet er å holde seg borte fra skadelige hindringer,
  * Spillerne må også å "løse" levelet ved samarbeid.

## A3:

**Møter**: Hver mandag 12:15-14:00, eventuelt torsdag morgen 08:15-09:00 hvis det trengs.

**Kommunikasjon mellom møter**: Holder styr på merge requests på discord, og tasks på trello (kanban oppsett).

**Arbeidsfordeling**: Skjer via trello board

**Oppfølging av arbeid**: Alt som merges trenger en pull-request, vi snakker om hva man har gjort denne uken hver mandag/torsdag på "standup".

**Deling av dokumenter/kodebase**: Alt skjer på gitlab

### Oversikt over forventet produkt

1. Vise et spillebrett
2. Vise spillere på spillebrett
3. Flytte spillerne (vha taster e.l.)
4. Spillerne interagerer med terreng
5. Spillerne har *poeng* og interagerer med poenggjenstander
6. Vise lava/vann; de skal interagere og spiller
7. Spiller kan dø (ved kontakt med lava/vann)
8. Mål for spillbrett er ett sted å gå
9. Nytt spillbrett når forrige er ferdig
10. Start-skjerm ved oppstart / game over

### Brukerhistorier:

Som spiller må jeg forstå hvilke karakter jeg er for å kunne styre spillfiguren.

Som spiller trenger jeg å forstå hvilken elementer i spillet jeg kan ta på/hva som er farlig.

Som programmør trenger jeg å skille platformer, vegger og hinder fra hverandre for å avgjøre om spillfiguren beveger seg rett, og om den burde ta skade.

Som programmør trenger jeg å kunne lage en eller flere "spillere" for å teste programvaren i både simple og kompliserte situvasjoner.

## A4: Kode

wip på branch `henbro/box2d` og branch `CreatePlayer`, og noe på `main`
