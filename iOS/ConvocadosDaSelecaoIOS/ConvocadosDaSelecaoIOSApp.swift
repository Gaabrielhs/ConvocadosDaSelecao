//
//  ConvocadosDaSelecaoIOSApp.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

@main
struct ConvocadosDaSelecaoIOSApp: App {
    @StateObject private var store = Store()
    
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(store)
        }
    }
    
}
