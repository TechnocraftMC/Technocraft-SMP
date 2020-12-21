package com.technocraft.server.util;

public class Help
{
    private String command;
    private String instruction;


    public Help(String command, String instruction)
    {
        this.command = command;
        this.instruction = instruction;
    }

    public String getCommand()
    {
        return command;
    }

    public String getInstruction()
    {
        return instruction;
    }
}
