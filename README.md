# pinboard-app (WIP)

A pinboard app that allows you to create and connect notes easily.

## .csv file saving convention

The program uses comma seperated value files to save the current state of the pinboard.

```
0. X coordinate of component
1. Y coordinate of component
2. The NoteType (NoteType.TitleDescriptionNote)
3. NoteType specific
4. NoteType specific
```

Example for a TitleDescriptionNote =\
`200,320,NoteType.TitleDescriptionNote,This is a Note Title,This is a really long Note Description`

Example for a TitleImageNote =\
`150,220,NoteType.TitleImageNote,This is another Title,C:/Path/To/Image`

Example for a ImageNote =\
`560,820,NoteType.ImageNote,C:/Path/To/Another/Image`
