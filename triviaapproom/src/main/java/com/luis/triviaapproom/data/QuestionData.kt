package com.luis.triviaapproom.data  // Define el paquete donde se encuentra este archivo

///*
//* Esta es una lista de preguntas que se usaban en la aplicación.
//* En la versión actual, este archivo ya no se usa, ya que las preguntas se obtienen de una API.
//*/


// Se define una lista inmutable de objetos Question
//val questions: Map<Int, Question> = mapOf(
//    10 to Question(
//        "Which author co-wrote 'The Communist Manifesto' alongside Karl Marx?",
//        listOf("Robert Owen", "Alexander Kerensky", "Paul Lafargue"),
//        "Friedrich Engels"
//    ),
//    10 to Question(
//        "The novel 'Jane Eyre' was written by what author?",
//        listOf("Emily Brontë", "Jane Austen", "Louisa May Alcott"),
//        "Charlotte Brontë"
//    ),
//    10 to Question(
//        "Who wrote the novel 'Moby-Dick'?",
//        listOf("William Golding", "William Shakespeare", "J. R. R. Tolkien"),
//        "Herman Melville"
//    ),
//    10 to Question(
//        "What is the fourth book of the Old Testament?",
//        listOf("Genesis", "Exodus", "Leviticus"),
//        "Numbers"
//    ),
//    10 to Question(
//        "Under what pseudonym did Stephen King publish five novels between 1977 and 1984?",
//        listOf("J. D. Robb", "Mark Twain", "Lewis Carroll"),
//        "Richard Bachman"
//    ),
//    10 to Question(
//        "What was the first ever entry written for the SCP Foundation collaborative writing project?",
//        listOf("SCP-001", "SCP-999", "SCP-1459"),
//        "SCP-173"
//    ),
//    10 to Question(
//        "In the Beatrix Potter books, what type of animal is Tommy Brock?",
//        listOf("Fox", "Frog", "Rabbit"),
//        "Badger"
//    ),
//    10 to Question(
//        "Which American author was also a budding travel writer and wrote of his adventures with his dog Charley?",
//        listOf("F. Scott Fitzgerald", "Ernest Hemingway", "William Faulkner"),
//        "John Steinbeck"
//    ),
//    10 to Question(
//        "Which of these books was NOT written by the Czech author Karel Čapek?",
//        listOf("The War with the Newts", "R.U.R. (Rossum's Universal Robots)", "Dashenka, or the Life of a Puppy"),
//        "Journey to the Center of the Earth"
//    ),
//    10 to Question(
//        "In the novel 1984, written by George Orwell, what is the name of the totalitarian regime that controls Oceania?",
//        listOf("Neo-Bolshevism", "Obliteration of the Self", "Earth Alliance"),
//        "INGSOC"
//    ),
//    10 to Question(
//        "Who was the author of the 1954 novel, 'Lord of the Flies'?",
//        listOf("Stephen King", "F. Scott Fitzgerald", "Hunter Fox"),
//        "William Golding"
//    ),
//    10 to Question(
//        "Who wrote the 'A Song of Ice And Fire' fantasy novel series?",
//        listOf("George Lucas", "George Orwell", "George Eliot"),
//        "George R. R. Martin"
//    ),
//    10 to Question(
//        "In Michael Crichton's novel 'Jurassic Park', John Hammond meets his demise at the claws of which dinosaur?",
//        listOf("Dilophosaurus", "Tyrannosaurus Rex", "Velociraptor"),
//        "Procompsognathus"
//    ),
//    10 to Question(
//        "George Orwell wrote this book, which is often considered a statement on government oversight.",
//        listOf("The Old Man and the Sea", "Catcher and the Rye", "To Kill a Mockingbird"),
//        "1984"
//    ),
//    10 to Question(
//        "Who wrote the young adult novel 'The Fault in Our Stars'?",
//        listOf("Stephenie Meyer", "Suzanne Collins", "Stephen Chbosky"),
//        "John Green"
//    ),
//    10 to Question(
//        "What was the pen name of novelist, Mary Ann Evans?",
//        listOf("George Orwell", "George Bernard Shaw", "George Saunders"),
//        "George Eliot"
//    ),
//    10 to Question(
//        "Which of the following is not a work authored by Fyodor Dostoevsky?",
//        listOf("Notes from the Underground", "Crime and Punishment", "The Brothers Karamazov"),
//        "Anna Karenina"
//    ),
//    10 to Question(
//        "By what name was the author Eric Blair better known?",
//        listOf("Aldous Huxley", "Ernest Hemingway", "Ray Bradbury"),
//        "George Orwell"
//    ),
//    10 to Question(
//        "In the book 'The Martian', how long was Mark Watney trapped on Mars (in Sols)?",
//        listOf("765 Days", "401 Days", "324 Days"),
//        "549 Days"
//    ),
//    11 to Question(
//        "Who plays Alice in the Resident Evil movies?",
//        listOf("Milla Jovovich", "Madison Derpe", "Milla Johnson"),
//        "Milla Jovovich"
//    ),
//    11 to Question(
//        "In the 'Jurassic Park' universe, what was the first dinosaur cloned by InGen in 1986?",
//        listOf("Triceratops", "Troodon", "Brachiosaurus"),
//        "Velociraptor"
//    ),
//    11 to Question(
//        "How old was Laurence Fishburne when Francis Ford Coppola cast him in Apocalypse Now (1979)?",
//        listOf("16", "18", "20"),
//        "14"
//    ),
//    11 to Question(
//        "Which sci-fi cult films plot concerns aliens attempting to prevent humans from creating a doomsday weapon?",
//        listOf("The Man from Planet X", "It Came from Outer Space", "The Day The Earth Stood Still"),
//        "Plan 9 from Outer Space"
//    ),
//    11 to Question(
//        "What name did Tom Hanks give to his volleyball companion in the film 'Cast Away'?",
//        listOf("Friday", "Jones", "Billy"),
//        "Wilson"
//    ),
//    11 to Question(
//        "What year did the James Cameron film 'Titanic' come out in theaters?",
//        listOf("1996", "1998", "1999"),
//        "1997"
//    ),
//    11 to Question(
//        "What does TIE stand for in reference to the TIE Fighter in 'Star Wars'?",
//        listOf("Twin Iron Engine", "Twin Intercepter Engine", "Twin Inception Engine"),
//        "Twin Ion Engine"
//    ),
//    11 to Question(
//        "Who did the score to the original Blade Runner?",
//        listOf("Kitaro", "Yanni", "Enya"),
//        "Vangelis"
//    ),
//    11 to Question(
//        "Who plays Jack Burton in the movie 'Big Trouble in Little China'?",
//        listOf("Patrick Swayze", "John Cusack", "Harrison Ford"),
//        "Kurt Russell"
//    ),
//    11 to Question(
//        "Who voiced the character Draco in the 1996 movie 'DragonHeart'?",
//        listOf("Dennis Quaid", "Pete Postlethwaite", "Brian Thompson"),
//        "Sean Connery"
//    ),
//    11 to Question(
//        "Who played the female lead in the 1933 film 'King Kong'?",
//        listOf("Jean Harlow", "Vivien Leigh", "Mae West"),
//        "Fay Wray"
//    ),
//    11 to Question(
//        "Who starred as Bruce Wayne and Batman in Tim Burton's 1989 movie 'Batman'?",
//        listOf("George Clooney", "Val Kilmer", "Adam West"),
//        "Michael Keaton"
//    ),
//    11 to Question(
//        "Who played Batman in the 1997 film 'Batman and Robin'?",
//        listOf("Michael Keaton", "Val Kilmer", "Christian Bale"),
//        "George Clooney"
//    ),
//    11 to Question(
//        "In The Lord of the Rings: The Fellowship of the Ring, which one of the following characters from the book was left out of the film?",
//        listOf("Strider", "Barliman Butterbur", "Celeborn"),
//        "Tom Bombadil"
//    ),
//    11 to Question(
//        "Who directed the Kill Bill movies?",
//        listOf("Arnold Schwarzenegger", "David Lean", "Stanley Kubrick"),
//        "Quentin Tarantino"
//    ),
//    11 to Question(
//        "Who wrote and directed the 1986 film 'Platoon'?",
//        listOf("Francis Ford Coppola", "Stanley Kubrick", "Michael Cimino"),
//        "Oliver Stone"
//    ),
//    11 to Question(
//        "In the 'Jurassic Park' universe, when did 'Jurassic Park: San Diego' begin construction?",
//        listOf("1988", "1986", "1993"),
//        "1985"
//    ),
//    11 to Question(
//        "What type of cheese, loved by Wallace and Gromit, had its sale prices rise after their successful short films?",
//        listOf("Cheddar", "Moon Cheese", "Edam"),
//        "Wensleydale"
//    ),
//    11 to Question(
//        "In the movie 'Blade Runner', what is the term used for human-like androids?",
//        listOf("Cylons", "Synthetics", "Skinjobs"),
//        "Replicants"
//    ),
//    11 to Question(
//        "In the 1979 British film 'Quadrophenia' what is the name of the main protagonist?",
//        listOf("Pete Townshend", "Franc Roddam", "Archie Bunker"),
//        "Jimmy Cooper"
//    ),
//    12 to Question(
//        "Who wrote the song 'You Know You Like It'?",
//        listOf("DJ Snake", "Steve Aoki", "Major Lazer"),
//        "AlunaGeorge"
//    ),
//    12 to Question(
//        "What was the name of the cold-war singer who has a song in Grand Theft Auto IV, and a wall landmark in Moscow for his memorial?",
//        listOf("Jimi Hendrix", "Brian Jones", "Vladimir Vysotsky"),
//        "Viktor Tsoi"
//    ),
//    12 to Question(
//        "Which one of these songs did the group 'Men At Work' NOT make?",
//        listOf("Down Under", "Who Can It Be Now?", "It's a Mistake"),
//        "Safety Dance"
//    ),
//    12 to Question(
//        "Which of the following bands is Tom DeLonge not a part of?",
//        listOf("Box Car Racer", "Blink-182", "Angels & Airwaves"),
//        "+44"
//    ),
//    12 to Question(
//        "Which band released the album 'Sonic Highways' in 2014?",
//        listOf("Coldplay", "Nickelback", "The Flaming Lips"),
//        "Foo Fighters"
//    ),
//    12 to Question(
//        "The 'British Invasion' was a cultural phenomenon in music where British boy bands became popular in the USA in what decade?",
//        listOf("50's", "40's", "30's"),
//        "60's"
//    ),
//    12 to Question(
//        "Who had a 1981 hit with the song 'Japanese Boy'?",
//        listOf("Toyah", "Sandra", "Madonna"),
//        "Aneka"
//    ),
//    12 to Question(
//        "From which country does the piano originate?",
//        listOf("Germany", "Austria", "France"),
//        "Italy"
//    ),
//    12 to Question(
//        "Kanye West's 'Gold Digger' featured which Oscar-winning actor?",
//        listOf("Alec Baldwin", "Dwayne Johnson", "Bruce Willis"),
//        "Jamie Foxx"
//    ),
//    12 to Question(
//        "Who sings the rap song 'Secret Wars Part 1'?",
//        listOf("MC Frontalot", "Busdriver", "Masta Killa"),
//        "The Last Emperor"
//    ),
//    12 to Question(
//        "Which French duo had UK hits in 1998 with the songs 'Sexy Boy', 'Kelly Watch The Stars' & 'All I Need'?",
//        listOf("Fire", "Earth", "Water"),
//        "Air"
//    ),
//    12 to Question(
//        "Kanye West at 2009 VMA's interrupted which celebrity?",
//        listOf("MF DOOM", "Beyonce", "Beck"),
//        "Taylor Swift"
//    ),
//    12 to Question(
//        "Which former boy-band star released hit solo single 'Angels' in 1997?",
//        listOf("Justin Timberlake", "Harry Styles", "Gary Barlow"),
//        "Robbie Williams"
//    ),
//    12 to Question(
//        "What was the name of Pink Floyd's first studio album?",
//        listOf("Ummagumma", "More", "Atom Heart Mother"),
//        "The Piper at the Gates of Dawn"
//    ),
//    12 to Question(
//        "What is the first track on Kanye West's 808s & Heartbreak?",
//        listOf("Welcome to Heartbreak", "Street Lights", "Heartless"),
//        "Say You Will"
//    ),
//    12 to Question(
//        "Which year was the album 'Year of the Snitch' by Death Grips released?",
//        listOf("2013", "2017", "2011"),
//        "2018"
//    ),
//    12 to Question(
//        "What is the stage name of English female rapper Mathangi Arulpragasam, who is known for the song 'Paper Planes'?",
//        listOf("K.I.A.", "C.I.A.", "A.I.A."),
//        "M.I.A."
//    ),
//    12 to Question(
//        "Which of these songs by artist Eminem contain the lyric 'Nice to meet you. Hi, my name is... I forgot my name!'?",
//        listOf("Without Me", "Kim", "Square Dance"),
//        "Rain Man"
//    ),
//    12 to Question(
//        "Which English guitarist has the nickname 'Slowhand'?",
//        listOf("Mark Knopfler", "Jeff Beck", "Jimmy Page"),
//        "Eric Clapton"
//    ),
//    12 to Question(
//        "What was Daft Punk's first studio album?",
//        listOf("Discovery", "Random Access Memories", "Human After All"),
//        "Homework"
//    )
//)