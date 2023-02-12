//
//  Time.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct Time: Identifiable {
    let id: UUID = UUID()
    let titulo: String
    let jogadores: [Jogador]
    var icon: String {
        (jogadores.count < jogadoresPorTime) ? "arrow.up.and.person.rectangle.portrait" : "person.3.fill"
    }
}
