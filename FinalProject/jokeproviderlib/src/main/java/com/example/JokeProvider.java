package com.example;

import java.util.Random;

public class JokeProvider {

    private static String[] jokeCollection = {"Headmaster: I've had complaints about you, Johnny, from all your teachers. What have you been doing? \n" +
            "Johnny: Nothing, sir. \n" +
            "Headmaster: Exactly",
            "Teacher: \"Nick, what is the past participle of the verb to ring?\" \n" +
                    "Nick: \"What do you think it is, Sir?\" \n" +
                    "Teacher: \"I don't think, I KNOW!\" \n" +
                    "Nick: \"I don't think I know either, Sir!\""
            ,"A: Hey, man! Please call me a taxi. \n" +
            "B: Yes, sir. You are a taxi",
            "A teacher asked a student to write 55. \n" +
                    "Student asked: How? \n" +
                    "Teacher: Write 5 and beside it another 5! \n" +
                    "The student wrote 5 and stopped. \n" +
                    "teacher: What are you waiting for? \n" +
                    "student: I don't know which side to write the other 5!",
            "Little Johnny: Teacher, can I go to the bathroom? \n" +
                    "Teacher: Little Johnny, MAY I go to the bathroom? \n" +
                    "Little Johnny: But I asked first! ",
            "Son: Dad, what is an idiot? \n" +
                    "Dad: An idiot is a person who tries to explain his ideas in such a strange and long way that another person who is listening to him can't understand him. Do you understand me? \n" +
                    "Son: No. "

    };
    public static String tellJoke()
    {
        Random rand = new Random();
        int index = rand.nextInt(6);
        String Joke = jokeCollection[index];
        return Joke ;
    }
}
