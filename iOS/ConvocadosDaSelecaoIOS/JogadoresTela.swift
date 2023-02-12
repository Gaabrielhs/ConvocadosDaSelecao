//
//  JogadoresTela.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct JogadoresTela: View {
    
    var body: some View {
        VStack {
            JogadoresLista().scrollDismissesKeyboard(.interactively)
            Adicionador().padding()
        }.toolbar {
            EditButton()
        }
    }
}

struct JogadoresTela_Previews: PreviewProvider {
    static var previews: some View {
        JogadoresTela().environmentObject(Store.buildDefault())
    }
}
