package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.services.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleNewsNotFound(ObjectNotFoundException onfe){
        ModelAndView modelAndView = new ModelAndView("exceptions/object-not-found");
        modelAndView.addObject("objectId", onfe.getId());
        return modelAndView;
    }
    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ModelAndView handlePlayerAlreadyExistsException(PlayerAlreadyExistsException ex) {
        ModelAndView model = new ModelAndView("exceptions/player-already-exists");
        model.addObject("message", ex.getMessage());
        model.addObject("firstName", ex.getFirstName());
        model.addObject("lastName", ex.getLastName());
        return model;
    }

    @ExceptionHandler(MaximumPlayersExceededException.class)
    public ModelAndView handleMaximumPlayersExceededException(MaximumPlayersExceededException ex) {
        ModelAndView model = new ModelAndView("exceptions/more-than-11-players");
        model.addObject("message", ex.getMessage());
        return model;
    }

    @ExceptionHandler(BudgetExceededException.class)
    public ModelAndView handleBudgetExceededException(BudgetExceededException ex) {
        ModelAndView model = new ModelAndView("exceptions/not-enough-money");
        model.addObject("message", ex.getMessage());
        return model;
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ModelAndView handlePlayerNotFoundException(PlayerNotFoundException ex) {
        ModelAndView model = new ModelAndView("exceptions/player-not-found");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
