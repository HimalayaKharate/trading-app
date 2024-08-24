package com.himluck.trading_app.service;

import com.himluck.trading_app.model.TwoFactorOTP;
import com.himluck.trading_app.model.User;
import com.himluck.trading_app.repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorServiceImpl implements TwoFactorOtpService{
    @Autowired
    private TwoFactorOtpRepository repository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        TwoFactorOTP twoFactorOTP = new TwoFactorOTP(uuidString, otp, user, jwt);
        return twoFactorOTP;
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> otp = repository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
        return twoFactorOtp.getOpt().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
        repository.delete(twoFactorOtp);
    }
}
