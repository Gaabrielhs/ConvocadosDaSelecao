//
//  JogadoresLista.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct JogadoresLista: View {
    @EnvironmentObject var store: Store
    
    var body: some View {
        List {
            ForEach(store.jogadores) { jogador in
                JogadorCard(jogador: jogador).id(jogador.id)
                    .swipeActions(edge: .trailing) {
                    Button(role: .destructive) {
                        store.jogadores.removeAll(where: { $0.id == jogador.id })
                    } label: {
                        Label("Delete", systemImage: "trash")
                    }
                }
            }
            .onMove {
                store.jogadores.move(fromOffsets: $0, toOffset: $1)
            }
            .onDelete {
                store.jogadores.remove(atOffsets: $0)
            }
        }
    }
}
