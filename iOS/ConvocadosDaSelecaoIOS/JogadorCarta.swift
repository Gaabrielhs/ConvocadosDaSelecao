//
//  JogadorCarta.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI

struct JogadorCarta: View {
    var body: some View {
        ZStack {
            Image("fifa_card").resizable().aspectRatio(contentMode: .fit)
            Text("GABRIEL")
                .font(.largeTitle)
                .bold()
                .foregroundColor(Color("fifa_card_title_color"))
                .padding(.top, 80)
            
            Text("90")
                .font(.system(size: 55, weight: .bold))
                .bold()
                .foregroundColor(Color("fifa_card_title_color"))
                .padding(.bottom, 370)
                .padding(.trailing, 180)
            
            Image(systemName: "person.fill")
                .resizable()
                .frame(width: 200, height: 200)
                .padding(.bottom, 180)
                .padding(.leading, 100)
            
            Image("BrazilFlag")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 55, height: 50)
                .padding(.trailing, 180)
                .padding(.bottom, 177)
        }
    }
}

struct JogadorCarta_Previews: PreviewProvider {
    static var previews: some View {
        JogadorCarta()
    }
}
