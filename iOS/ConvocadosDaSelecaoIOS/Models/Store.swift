//
//  Store.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import SwiftUI
import AVKit

let jogadoresPorTime = 5
let extratorDeNomesExp = #/(?m)^[0-9]+[ ]*[-]+[ ]+(?<nome>.*)$/#
let jogadoresSeparador = /(?i)jogador/


class Store: ObservableObject {
    @Published var times: [Time]
    @Published var jogadores: [Jogador]
    
    
    init(times: [Time] = [], jogadores: [Jogador] = []) {
        self.times = times
        self.jogadores = jogadores
    }
    
    func adicionarJogador(input: String?) {
        /// Verifica se não é nil
        guard let nome = input?.trimmingCharacters(in: .whitespaces) else { return }
        
        /// Verifica se não é vazio
        if (nome.isEmpty) { return }
        
        /// Verifica se é inserção múltipla
        if (nome.contains(extratorDeNomesExp)) {
            load(from: nome)
            return
        }
    
        /// Verifica se já existe case insensitive
        let jaExiste = jogadores.contains(where: { $0.name.caseInsensitiveCompare(nome) == .orderedSame })
        if (jaExiste) { return }
        
        /// Adiciona o jogador
        jogadores.append(
            Jogador(name: nome)
        )
    }
     
    func sortear() {
        var jogadoresParaSortear = jogadores
        
        let totalTimes = Int(ceil(Double(jogadores.count) / Double(jogadoresPorTime)))
        
        let times = (0..<totalTimes).map { index in
            let quantidadeMaximaDeJogadores = jogadoresParaSortear.count.coerceAtMost(maximumValue: jogadoresPorTime)
            let jogadores = (0..<quantidadeMaximaDeJogadores).map { _ in
                let jogadorSorteado = jogadoresParaSortear.randomElement()
                jogadoresParaSortear.removeAll(where: { $0.id == jogadorSorteado?.id })
                return jogadorSorteado!
            }
            let titulo = (jogadores.count < jogadoresPorTime) ? "Reservas" : "Time \(index + 1)"
            return Time(titulo: titulo, jogadores: jogadores)
        }
        
        self.times = times
    }
    
    func load(from clipboard: String?) {
        guard let string = clipboard else { return }
        let jogadores = string.split(separator: jogadoresSeparador).last!
        let matches = jogadores.matches(of: extratorDeNomesExp)
        matches.forEach { match in
            let nome = String(match.output.nome)
            adicionarJogador(input: nome)
        }
    }
    
    static func buildDefault() -> Store {
        let jogadores = [
            Jogador(name: "Gabriel"),
            Jogador(name: "Jogador 2"),
            Jogador(name: "Jogador 3")
        ]
        return Store(
            times: [
                Time(titulo: "Time 1", jogadores: jogadores),
                Time(titulo: "Time 2", jogadores: jogadores + [Jogador(name: "Ultimo")])
            ],
            jogadores: jogadores
        )
    }
}
