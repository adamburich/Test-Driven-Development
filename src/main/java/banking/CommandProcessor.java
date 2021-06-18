package banking;

public class CommandProcessor {

    private Bank bank;

    private CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
    private DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
    private WithdrawalCommandProcessor withdrawalCommandProcessor = new WithdrawalCommandProcessor(bank);
    private TransferCommandProcessor transferCommandProcessor = new TransferCommandProcessor(bank);
    private PassTimeCommandProcessor passTimeCommandProcessor = new PassTimeCommandProcessor(bank);

    public CommandProcessor(Bank b) {
        bank = b;
    }

    public void issue_command(Command c) {
        types this_command = types.valueOf(c.getClass().getSimpleName());
        switch (this_command) {
            case CreateCommand:
                createCommandProcessor.issue_command(c);
            case DepositCommand:
                depositCommandProcessor.issue_command(c);
            case WithdrawalCommand:
                withdrawalCommandProcessor.issue_command(c);
            case TransferCommand:
                transferCommandProcessor.issue_command(c);
            case PassCommand:
                passTimeCommandProcessor.issue_command(c);
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
