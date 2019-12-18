graph[
    node[
        id 1
        label "Which age group does the patient belong to?"
        display "radio"
    ]
    edge[
        source 1
        target 2
        label "18 to 49 years old"
    ]
    edge[
        source 1
        target 2
        label "50 years or older"
    ]

    node[
            id 2
            label "Does the patient suffer from any of the following conditions?"
            display "checkbox"
        ]
        edge[
            source 2
            target 3
            label "diabetes"
        ]
        edge[
            source 2
            target 3
            label "hypertension"
        ]
        edge[
            source 2
            target 3
            label "chronic pain"
        ]

    node[
         id 3
         label "Does the patient take any of the following medication?"
            display "checkbox"
        ]
        edge[
            source 3
            target 6
            label "warfarin"
        ]
        edge[
            source 3
            target 6
            label "vascocardin"
        ]
        edge[
            source 3
            target 5
            label "pain killers"
        ]

    node[
            id 5
            label "Does the patient have a headache/sore throat/ear pain?"
            display "radio"
        ]
        edge[
            source 5
            target 4
            label "yes"
        ]
        edge[
            source 5
            target 6
            label "no"
        ]


    node[
            id 4
            label "exit"
            display "terminate"
     ]

        node[
                id 6
                label "Does the patient have exercise regularly"
                display "radio"
            ]
            edge[
                source 6
                target 4
                label "yes"
            ]
            edge[
                source 6
                target 4
                label "no"
            ]



]

