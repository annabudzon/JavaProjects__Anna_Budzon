package threads;

import threads.Crawler.STATUS;

public class ParellelLogger implements Logger {

    private Logger[] loggers;

    public ParellelLogger(Logger[] loggers) {
        this.loggers = loggers;
    }

    @Override
    public void log(STATUS status, Student student) {
        for (Logger l : loggers) {
            l.log(status, student);
        }
    }

    @Override
    public void log(STATUS status, int iteracja) {
        for (Logger l : loggers) {
            l.log(status, iteracja);
        }
    }

    @Override
    public void log(STATUS status) {
        for (Logger l : loggers) {
            l.log(status);
        }
    }

}
