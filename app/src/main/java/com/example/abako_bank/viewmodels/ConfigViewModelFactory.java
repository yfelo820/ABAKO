package com.example.abako_bank.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.repository.ConfiguracionRepository;

public class ConfigViewModelFactory implements ViewModelProvider.Factory{

@NonNull
@Override
@SuppressWarnings("unchecked")
public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ConfigViewModel.class)) {
        return (T) new ConfigViewModel(ConfiguracionRepository.getInstance());
        } else {
        throw new IllegalArgumentException("Unknown ViewModel class");
        }
        }
        }
