package banking;

public class CommandProcessor {

    private Bank bank;
    private CreateCommandProcessor createCommandProcessor;
    private DepositCommandProcessor depositCommandProcessor;
    private WithdrawalCommandProcessor withdrawalCommandProcessor;
    private TransferCommandProcessor transferCommandProcessor;
    private PassTimeCommandProcessor passTimeCommandProcessor;

    public CommandProcessor(Bank b) {
        this.bank = b;
        this.createCommandProcessor = new CreateCommandProcessor(bank);
        this.depositCommandProcessor = new DepositCommandProcessor(bank);
        this.withdrawalCommandProcessor = new WithdrawalCommandProcessor(bank);
        this.transferCommandProcessor = new TransferCommandProcessor(bank);
        this.passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
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
                issue_command(c.identify_type());
                break;
        }
    }
}
