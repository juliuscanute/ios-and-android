//
//  ViewController.swift
//  GeoQuiz
//
//  Created by Julius Canute on 18/6/19.
//  Copyright © 2019 Julius Canute. All rights reserved.
//

import UIKit
import Toast_Swift

class QuizViewController: UIViewController {

    private let questionBank: [Question] = [
        Question(questionKey: "question_australia", isAnswerTrue: true),
        Question(questionKey: "question_oceans", isAnswerTrue: true),
        Question(questionKey: "question_mideast", isAnswerTrue: false),
        Question(questionKey: "question_africa", isAnswerTrue: false),
        Question(questionKey: "question_americas", isAnswerTrue: true),
        Question(questionKey: "question_asia", isAnswerTrue: true)
    ]
    
    private var currentIndex: Int = 0
    
    private var answers:[Int: Bool]=[Int: Bool]()
    
    var isCheater: Bool = false
    
    @IBOutlet var questionLabel: UILabel!
    @IBOutlet var trueButton: UIButton!
    @IBOutlet var falseButton: UIButton!
    
    @IBAction func showNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.count
        updateQuestion()
    }
    
    @IBAction func showPreviousQuestion() {
        if currentIndex == 0 {
            currentIndex = questionBank.count - 1
        }
        else {
            currentIndex = currentIndex - 1
        }
        updateQuestion()
    }
    
    @IBAction func onTrueClick() {
        checkAnswer(userPressedTrue: true)
    }
    
    @IBAction func onFalseClick() {
        checkAnswer(userPressedTrue: false)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        updateQuestion()
    }
    
    private func checkAnswer(userPressedTrue: Bool) {
        let answerIsTrue = questionBank[currentIndex].isAnswerTrue
        var messageKey:String
        if isCheater {
            messageKey = "judgment_toast"
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageKey = "correct"
                answers[currentIndex] = true
            } else {
                messageKey = "incorrect"
                answers[currentIndex] = false
            }
        }
        self.view.makeToast(getString(key: messageKey))
        checkScore()
        updateQuestion()
    }
    
    private func checkScore() {
        if(answers.count == questionBank.count){
            var correctAnswers: Int = 0
            for (_, isCorrect) in answers {
                if (isCorrect) {
                    correctAnswers+=1
                }
            }
            let score = Int((Double(correctAnswers)/Double(answers.count))*100.0)
            self.view.makeToast(getString(key: "quiz_score",data: score))
        }
    }
    
    private func updateQuestion() {
        isCheater = false
        let question = getString(key: questionBank[currentIndex].questionKey)
        questionLabel.text = question
        let buttonsEnabled = answers[currentIndex] == nil
        trueButton.isEnabled = buttonsEnabled
        falseButton.isEnabled = buttonsEnabled
    }

    private func getString(key: String) -> String {
        return NSLocalizedString(key,comment: "Read localized value")
    }
    
    private func getString(key: String,data: Int) -> String {
        return String(format: NSLocalizedString(key,comment: "Read localized value"),data)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let cheatViewController = segue.destination as? CheatViewController
        cheatViewController?.questionWithAnswer = questionBank[currentIndex]
    }
    
    @IBAction func didCheat(sender: UIStoryboardSegue) {
        let cheatViewController = sender.source as? CheatViewController
        isCheater = cheatViewController?.cheater ?? false
    }
    
}

