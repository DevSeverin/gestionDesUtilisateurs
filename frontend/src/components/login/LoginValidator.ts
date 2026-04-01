import type { User } from "../../models/User";


export const validate = (user: User): UserErrors[] => {
    const errors[] = new UserErrors();
    if (!user.name) errors.name = "Obligatoire";
    if (!user.email) errors.email = "Obligatoire";
    // if (!user.password) errors.password = "Obligatoire";
   
    // errors.questions = [];
    // quiz.questions.forEach(question => {
    //     const questionErrors = new QuizQuestionErrors();
    //     if (!question.question) questionErrors.question = MANDATORY;
    //     if (question.answerIndex === -1) questionErrors.answer = AN_OPTION_MUST_BE_SELECTED;
    //     question.options.forEach(value => questionErrors.options.push(!value ? MANDATORY : ''));
    //     errors.questions.push(questionErrors);
    // });

    return errors;
}

export class UserErrors {
    name: string = "";
    email: string = "";
    password: string = "";

    isNotEmpty = () => this.name || this.email || this.password;
}
