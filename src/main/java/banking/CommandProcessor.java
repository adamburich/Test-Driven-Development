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
        if (c instanceof CreateCommand) {
            createCommandProcessor.issue_command((CreateCommand) c);
        } else if (c instanceof DepositCommand) {
            depositCommandProcessor.issue_command((DepositCommand) c);
        } else if (c instanceof PassCommand) {
            passTimeCommandProcessor.issue_command((PassCommand) c);
        } else if (c instanceof TransferCommand) {
            transferCommandProcessor.issue_command((TransferCommand) c);
        } else if (c instanceof WithdrawalCommand) {
            withdrawalCommandProcessor.issue_command((WithdrawalCommand) c);
        }
    }
}
