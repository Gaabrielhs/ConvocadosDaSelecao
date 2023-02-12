//
//  ContentView.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI
import AVKit

enum Tab {
    case Jogadores
    case Times
}

struct ContentView: View {
    
    @EnvironmentObject var store: Store
    @State var audioPlayer: AVAudioPlayer?
    @State var activeTab: Tab = .Jogadores
    
    var body: some View {
        TabView(selection: $activeTab) {
            NavigationView {
                JogadoresTela()
                    .navigationTitle("Jogadores")
                    .navigationBarTitleDisplayMode(.automatic)
            }.tabItem {
                Label("Jogadores", systemImage: "figure.soccer")
            }.tag(Tab.Jogadores)
            
            NavigationView {
                TimesTela(onSortear: onSortear).navigationTitle("Times")
            }.tabItem {
                Label("Times", systemImage: "person.3.fill")
            }.tag(Tab.Times)
        }
        .onAppear {
            print("Appear with clipboard")
            let pasteboard = UIPasteboard.general
            store.load(from: pasteboard.string)
        }
        .onShake {
            onSortear()
        }
    }
    
    func onSortear() {
        Task {
            UIImpactFeedbackGenerator(style: .heavy).impactOccurred()
            try await Task.sleep(nanoseconds: 300_000_000)
            UIImpactFeedbackGenerator(style: .medium).impactOccurred()
        }
        store.sortear()
        playDiceSound()
        activeTab = Tab.Times
    }
    
    func playDiceSound() {
        let sound = Bundle.main.path(forResource: "rolling_dice", ofType: "mp3")
        
        do {
            audioPlayer = try! AVAudioPlayer(contentsOf: URL(fileURLWithPath: sound!))
            audioPlayer?.play()
        } catch {
          print("Error")
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(Store.buildDefault())
    }
}
