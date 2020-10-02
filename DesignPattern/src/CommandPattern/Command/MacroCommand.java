package CommandPattern.Command;

public abstract class MacroCommand implements Command{
    private Command[] commands;
    MacroCommand() {
        commands = new Command[5];
    }

    @Override
    public void execute() {
        for(Command command: commands)
            command.execute();
    }

    @Override
    public void undo() {
        for(Command command: commands)
            command.undo();
    }
}
