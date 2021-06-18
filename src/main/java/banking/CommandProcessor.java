package banking;

public class CommandProcessor {

    private Bank bank;
    private CreateCommandProcessor createCommandProcessor;
    private DepositCommandProcessor depositCommandProcessor;
    private WithdrawalCommandProcessor withdrawalCommandProcessor;
    private TransferCommandProcessor transferCommandProcessor;
    private PassTimeCommandProcessor passTimeCommandProcessor;

    public CommandProcessor(Bank b) {
        bank = b;
        createCommandProcessor = new CreateCommandProcessor(bank);
        depositCommandProcessor = new DepositCommandProcessor(bank);
        withdrawalCommandProcessor = new WithdrawalCommandProcessor(bank);
        transferCommandProcessor = new TransferCommandProcessor(bank);
        passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
    }

    public void issue_command(Command c) {
        String this_command = c.getClass().getSimpleName();
        switch (this_command) {
            case "CreateCommand":
                createCommandProcessor.issue_command((CreateCommand) c);
                break;
            case "DepositCommand":
                depositCommandProcessor.issue_command((DepositCommand) c);
                break;
            case "WithdrawalCommand":
                withdrawalCommandProcessor.issue_command((WithdrawalCommand) c);
                break;
            case "TransferCommand":
                transferCommandProcessor.issue_command((TransferCommand) c);
                break;
            case "PassCommand":
                passTimeCommandProcessor.issue_command((PassCommand) c);
                break;
            default:
                break;
        }
        /**
         if (c instanceof CreateCommand) {
         createCommandProcessor.issue_command(c);
         }
         if (c instanceof DepositCommand) {
         depositCommandProcessor.issue_command(c);
         }
         if (c instanceof PassCommand) {
         passTimeCommandProcessor.issue_command(c);
         }
         if (c instanceof TransferCommand) {
         transferCommandProcessor.issue_command(c);
         }
         if (c instanceof WithdrawalCommand) {
         withdrawalCommandProcessor.issue_command(c);
         }*/
    }

    enum types {
        CreateCommand, DepositCommand, WithdrawalCommand, PassCommand, TransferCommand
    }
}
