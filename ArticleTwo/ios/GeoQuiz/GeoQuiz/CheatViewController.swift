//
//  CheatViewController.swift
//  GeoQuiz
//
//  Created by Julius Canute on 19/6/19.
//  Copyright © 2019 Julius Canute. All rights reserved.
//

import UIKit
import Toast_Swift

class CheatViewController: UIViewController {
    var questionWithAnswer: Question?
    var cheater:Bool = false
    @IBOutlet var answerLabel: UILabel!
    
    @IBAction func showAnswer() {
        let answer: Bool = questionWithAnswer?.isAnswerTrue ?? false
        answerLabel.text = String(answer)
        cheater = true
    }
    
}
