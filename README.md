KordDnD is a discord that provides various commands to provide DnD related informations to discord servers via discord slash commands.

## Docker
You can deploy the bot using docker:
```
docker run --name=KordDnD -e TOKEN=DISCORD_BOT_TOKEN noxdecima/korddnd:latest
```
Where ```DISCORD_BOT_TOKEN``` should be the bot token that discord provides for your bot application.
You can create such an application [here](https://discord.com/developers/applications).


## Commands

#### monster
This command returns information about the first found monster which matches ```NAME```.
```
/monster NAME
```

#### spell
This command returns information about the first found spell which matches ```NAME```.
```
/spell NAME
```

## Credits

This code is based on [Kord Extensions bot template](https://github.com/Kord-Extensions/template).

The bot gathers its DnD data from the free [dnd5eapi](http://www.dnd5eapi.co).
