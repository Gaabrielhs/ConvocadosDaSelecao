//
//  TimeCard.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct TimeCard: View {
    let time: Time
    var body: some View {
        GroupBox(label: Label(time.titulo, systemImage: time.icon)) {
            Spacer().frame(height: 16)
            VStack(spacing: 8) {
                ForEach(time.jogadores) { jogador in
                    JogadorCard(jogador: jogador).frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            Spacer()
        }
    }
}

struct TimeCard_Previews: PreviewProvider {
    static var previews: some View {
        TimeCard(
            time: Time(titulo: "Time 1", jogadores: [Jogador(name: "Gabriel"), Jogador(name: "Jogador 2")])
        )
    }
}
