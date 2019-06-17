//
//  ViewController.swift
//  GeoQuiz
//
//  Created by Julius Canute on 16/6/19.
//  Copyright © 2019 Julius Canute. All rights reserved.
//

import UIKit
import Toast_Swift

class QuizController: UIViewController {

    @IBAction func trueButtonListener(sender: UIButton){
        self.view.makeToast(getString(key: "correct"))
    }
    
    @IBAction func falseButtonListener(sender: UIButton){
        self.view.makeToast(getString(key: "incorrect"))
    }
    
    private func getString(key: String) -> String {
        return NSLocalizedString(key,comment: "Read localized value")
    }
}

