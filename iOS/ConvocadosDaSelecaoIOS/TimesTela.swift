//
//  TimesTela.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI
import AudioToolbox


struct TimesTela: View {
    
    @EnvironmentObject var store: Store
    
    let onSortear: () -> Void
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        VStack {
            LazyVGrid(columns: columns, alignment: .center) {
                ForEach(store.times) { time in
                    TimeCard(time: time)
                }
            }
            Spacer()
            Button(action: onSortear) {
                Label("Sortear", systemImage: "dice")
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
        }
        .padding()
    }
}

struct TimesTela_Previews: PreviewProvider {
    
    static var previews: some View {
        TimesTela {}.environmentObject(Store.buildDefault())
    }
}
