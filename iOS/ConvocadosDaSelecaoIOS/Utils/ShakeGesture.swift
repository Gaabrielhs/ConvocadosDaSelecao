//
//  ShakeGesture.swift
//  ConvocadosDaSelecaoIOS
//
//  Created by Gabriel Henrique on 11/02/23.
//

import UIKit
import SwiftUI

extension Notification.Name {
    static let shakeEnded = Notification.Name("ShakeEnded")
}

extension UIWindow {
    open override func motionEnded(_ motion: UIEvent.EventSubtype, with: UIEvent?) {
        super.motionEnded(motion, with: with)
        guard motion == .motionShake else { return }
        NotificationCenter.default.post(name: .shakeEnded, object: nil)
    }
}

struct ShakeDetector: ViewModifier {
    let onShake: () -> Void
    
    func body(content: Content) -> some View {
        content.onReceive(NotificationCenter.default.publisher(for: .shakeEnded)) { _ in
            onShake()
        }
    }
}

extension View {
    func onShake(perform action: @escaping () -> Void) -> some View {
        self.modifier(ShakeDetector(onShake: action))
    }
}
