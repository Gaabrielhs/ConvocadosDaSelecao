//
//  Int+Extension.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import Foundation

extension Int {
    func coerceAtMost(maximumValue: Int) -> Int {
        if (self > maximumValue) {
            return maximumValue
        } else {
            return self
        }
        
        //7 maximum value 5 = 5
        //3 maximum value 5 = 3
        //ou
//        return (self > maximumValue) ? maximumValue : self
    }
}
