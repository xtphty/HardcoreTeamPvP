HardcoreTeamPvP
==========
Based on https://github.com/marcelo-mason/SimpleClans

Adds the following extra commands:
  - `/clan restrict enable` - restrict server to clan players only, all others are kicked
  - `/clan restrict disable` - disable clan only restriction, solo players can reconnect
  
Adds the following config.yml options: 
  - `clan.killintervalcountdown` - grace period (in minutes) before teams start being eliminated.  Default: 30 minutes
  - `clan.killinterval` - interval (in minutes) between teams being eliminated, following the grace period. Default: 30 minutes
   
Other changes: 
  - The player list and overhead names now show clan tags and colors according to player's team.
  - SimpleClan clans are kept in sync with the Minecraft stock teams to allow the above.
  - Clan tag colors are now assigned automatically to keep them unique. 
