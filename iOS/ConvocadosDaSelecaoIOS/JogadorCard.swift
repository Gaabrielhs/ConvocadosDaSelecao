//
//  JogadorCard.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct JogadorCard : View {
    let jogador: Jogador
    
    var body: some View {
        HStack {
            Image(systemName: "soccerball")
            Text(jogador.name).font(.callout)
        }
    }
}

struct JogadorCard_Previews: PreviewProvider {
    static var previews: some View {
        JogadorCard(jogador: Jogador(name: "Gabriel"))
    }
}
