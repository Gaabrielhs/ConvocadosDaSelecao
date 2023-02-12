//
//  Adicionador.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct Adicionador: View {
    
    @EnvironmentObject private var store: Store
    @State private var jogador: String = ""
    
    var body: some View {
        HStack {
            TextField("Jogador", text: $jogador, axis: .vertical)
                .padding(4)
                .padding([.leading, .trailing], 8)
                .overlay(RoundedRectangle(cornerRadius: 16).stroke(.gray, lineWidth: 1).opacity(0.5))
                .autocapitalization(.none)
                .onSubmit(onSubmit)
                .submitLabel(.done)
            
            Button(action: onSubmit) {
                Image(systemName: "plus").imageScale(.large)
            }
        }
    }
    
    func onSubmit() {
        store.adicionarJogador(input: jogador)
        jogador = ""
    }
}

struct Adicionador_Previews: PreviewProvider {
    static var previews: some View {
        Adicionador().environmentObject(Store.buildDefault())
    }
}
