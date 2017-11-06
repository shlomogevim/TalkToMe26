package itAboutTalking.TalkToMe17;


public class Sentence {
        private String numSentence,titleSentence,bodySentence;

        public Sentence(String numSentence, String titleSentence, String bodySentence) {
            this.numSentence = numSentence;
            this.titleSentence = titleSentence;
            this.bodySentence = bodySentence;
        }

        public String getNumSentence() {
            return numSentence;
        }

        public void setNumSentence(String numSentence) {
            this.numSentence = numSentence;
        }

        public String getTitleSentence() {
            return titleSentence;
        }

        public void setTitleSentence(String titleSentence) {
            this.titleSentence = titleSentence;
        }

        public String getBodySentence() {
            return bodySentence;
        }

        public void setBodySentence(String bodySentence) {
            this.bodySentence = bodySentence;
        }
    }




